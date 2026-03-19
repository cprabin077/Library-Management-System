package com.prabin.lbs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.prabin.lbs.exception.BookException;
import com.prabin.lbs.mapper.BookMapper;
import com.prabin.lbs.modal.BookModal;
import com.prabin.lbs.payload.dto.BookDTO;
import com.prabin.lbs.payload.request.BookSearchRequest;
import com.prabin.lbs.payload.response.PageResponse;
import com.prabin.lbs.repository.BookRepository;
import com.prabin.lbs.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

	private final BookRepository bookRepository;
	private final BookMapper bookMapper;
	
	@Override
	public BookDTO createBook(BookDTO bookDTO) throws BookException {
		
		if(bookRepository.existsByIsbn(bookDTO.getIsbn())) {
			throw new BookException("book with isbn "+ bookDTO.getIsbn()+"already exists");
		}
		BookModal bookModal = bookMapper.toEntity(bookDTO);
		
		// 
		bookModal.isAvailableCopiesValid();
		
		BookModal savedBookModal = bookRepository.save(bookModal);
		return bookMapper.toDTO(savedBookModal);
	}

	@Override
	public List<BookDTO> createBooksBulk(List<BookDTO> bookDTOs) throws BookException {
		
		List<BookDTO> createdBooks = new ArrayList<>();
		for(BookDTO bookDTO:bookDTOs) {
			BookDTO book = createBook(bookDTO);
			createdBooks.add(book);
		}
		return createdBooks;
	}

	@Override
	public BookDTO getBookById(Long bookId) throws BookException {
		
		BookModal book =  bookRepository.findById(bookId).orElseThrow(()-> new BookException("book not found"));
		
		return bookMapper.toDTO(book);
	}

	@Override
	public BookDTO getBookByISBN(String isbn) throws BookException {
		BookModal book =  bookRepository.findByIsbn(isbn).orElseThrow(()-> new BookException("book not found"));
		
		return bookMapper.toDTO(book);
	}

	@Override
	public BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BookException {
		BookModal existingBook = bookRepository.findById(bookId)
				.orElseThrow(()-> new BookException("book not found")
		);
		bookMapper.updateEntityFromDTO(bookDTO, existingBook);
		existingBook.isAvailableCopiesValid();
		BookModal savedBook = bookRepository.save(existingBook);
		
		return bookMapper.toDTO(savedBook);	
	}

	@Override
	public void deleteBook(Long bookId) throws BookException {
		BookModal existingBook = bookRepository.findById(bookId)
				.orElseThrow(()-> new BookException("book not found")
		);
		existingBook.setActive(false);
		bookRepository.save(existingBook);
	}

	@Override
	public void hardDeleteBook(Long bookId) throws BookException {
		BookModal existingBook = bookRepository.findById(bookId)
				.orElseThrow(()-> new BookException("book not found")
		);
		bookRepository.delete(existingBook);
	}

	@Override
	public PageResponse<BookDTO> searchBooksWithFilters(BookSearchRequest searchRequest) {
		Pageable pageable = createPageable(searchRequest.getPage(),
				searchRequest.getSize(),
				searchRequest.getSortBy(),
				searchRequest.getSortDirection());
		
		boolean availableOnly = Boolean.TRUE.equals(searchRequest.getAvailableOnly());

		Page<BookModal> bookPage = bookRepository.searchBookWithFilters(
		        searchRequest.getSearchTerm(),
		        searchRequest.getGenreId(),
		        availableOnly,
		        pageable
		);
		return convertToPageResponse(bookPage);
	}

	@Override
	public long getTotalActiveBooks() {
		
		return bookRepository.countByActiveTrue();
	}

	@Override
	public long getTotalAvailableBooks() {
		
		return bookRepository.countAvailableBooks();
	}
	
	private Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
		size = Math.min(size, 10);
		size=Math.max(size, 1);
		
		Sort sort = sortDirection.equalsIgnoreCase("ASC")
				?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		return PageRequest.of(page, size, sort);
	}
	
	private PageResponse<BookDTO> convertToPageResponse(Page<BookModal> books){
		List<BookDTO> bookDTOS = books.getContent()
				.stream()
				.map(bookMapper::toDTO)
				.collect(Collectors.toList());
		
		return new PageResponse<BookDTO>(bookDTOS, 
				books.getNumber(),
				books.getSize(),
				books.getTotalElements(),
				books.getTotalPages(),
				books.isLast(),
				books.isFirst(),
				books.isEmpty()
				);
	}
}
