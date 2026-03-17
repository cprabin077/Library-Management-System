package com.prabin.lbs.mapper;

import org.springframework.stereotype.Component;

import com.prabin.lbs.exception.BookException;
import com.prabin.lbs.modal.BookModal;
import com.prabin.lbs.modal.GenreModal;
import com.prabin.lbs.payload.dto.BookDTO;
import com.prabin.lbs.repository.GenreRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookMapper {
	
	private final GenreRepository genreRepository;
	
	public BookDTO toDTO(BookModal bookModal) {
		if(bookModal==null) {
			return null;
		}
		BookDTO dto = BookDTO.builder()
				.id(bookModal.getId())
				.title(bookModal.getTitle())
				.author(bookModal.getAuthor())
				.isbn(bookModal.getIsbn())
				
				.genreId(bookModal.getGenreModal().getId())
				.genreName(bookModal.getGenreModal().getName())
				.genreCode(bookModal.getGenreModal().getCode())
				
				.publisher(bookModal.getPublisher())
				.publicationDate(bookModal.getPublishedDate())
				.language(bookModal.getLanguage())
				.pages(bookModal.getPages())
				.description(bookModal.getDescription())
				.totalCopies(bookModal.getTotalCopies())
				.availableCopies(bookModal.getAvailableCopies())
				.price(bookModal.getPrice())
				.coverImageUrl(bookModal.getCoverImageUrl())
				.active(bookModal.getActive())
				.createdAt(bookModal.getCreatedAt())
				.updatedAt(bookModal.getUpdatedAt())
				.build();
				
				
				return dto;
		
	}
	
	public BookModal toEntity(BookDTO dto) throws BookException{
		if(dto == null) {
			return null;
		}
		BookModal bookModal = new BookModal();
		bookModal.setId(dto.getId());
		bookModal.setIsbn(dto.getIsbn());
		bookModal.setTitle(dto.getTitle());
		bookModal.setAuthor(dto.getAuthor());
		
		// Map genre - fetch from database using genreId
		if(dto.getGenreId() != null) {
			GenreModal genreModal = genreRepository.findById(dto.getGenreId()) 
					.orElseThrow(()-> new BookException("Genre with ID" + dto.getGenreId()+"not found"));
			bookModal.setGenreModal(genreModal);
		}
		
		bookModal.setPublisher(dto.getPublisher());
		bookModal.setPublishedDate(dto.getPublicationDate());
		bookModal.setLanguage(dto.getLanguage());
		bookModal.setPages(dto.getPages());
		bookModal.setDescription(dto.getDescription());
		bookModal.setTotalCopies(dto.getTotalCopies());
		bookModal.setAvailableCopies(dto.getAvailableCopies());
		bookModal.setPrice(dto.getPrice());
		bookModal.setCoverImageUrl(dto.getCoverImageUrl());
		bookModal.setActive(true);

		return bookModal;
		
	}
	
	public void updateEntityFromDTO(BookDTO dto, BookModal bookModal) throws BookException {
		
		if(dto == null || bookModal == null) {
			return;
			
		}
		
		// ISBN should not be updated
		
		bookModal.setTitle(dto.getTitle());
		bookModal.setAuthor(dto.getAuthor());
		
		// Update Genre if provided
		if(dto.getGenreId() != null) {
			GenreModal genreModal = genreRepository.findById(dto.getGenreId())
					.orElseThrow(()-> new BookException("Genre with  ID "+ dto.getGenreId()+"not found"));
			bookModal.setGenreModal(genreModal);
		}
		
		bookModal.setPublishedDate(dto.getPublicationDate());
		bookModal.setPublisher(dto.getPublisher());
		bookModal.setLanguage(dto.getLanguage());
		bookModal.setPages(dto.getPages());
		bookModal.setDescription(dto.getDescription());
		bookModal.setTotalCopies(dto.getTotalCopies());
		bookModal.setAvailableCopies(dto.getAvailableCopies());
		bookModal.setPrice(dto.getPrice());
		bookModal.setCoverImageUrl(dto.getCoverImageUrl());
		
		if(dto.getActive() != null) {
			bookModal.setActive(dto.getActive());
		}
		
	}

}
