package com.prabin.lbs.service;

import java.util.List;

import com.prabin.lbs.exception.BookException;
import com.prabin.lbs.payload.dto.BookDTO;
import com.prabin.lbs.payload.request.BookSearchRequest;
import com.prabin.lbs.payload.response.PageResponse;

public interface BookService {
	
	BookDTO createBook(BookDTO bookDTO) throws BookException;
	
	List<BookDTO> createBooksBulk(List<BookDTO> bookDTOs) throws BookException;
	
	BookDTO getBookById(Long bookId) throws BookException;
	
	BookDTO getBookByISBN(String isbn) throws BookException;
	
	BookDTO updateBook(Long bookId,  BookDTO bookDTO) throws BookException;
	
	void deleteBook(Long bookId) throws BookException;
	
	void hardDeleteBook(Long bookId) throws BookException;
	
	PageResponse<BookDTO> searchBooksWithFilters(
			BookSearchRequest searchRequest
	);
	
	long getTotalActiveBooks();
	
	long getTotalAvailableBooks();
	
}
