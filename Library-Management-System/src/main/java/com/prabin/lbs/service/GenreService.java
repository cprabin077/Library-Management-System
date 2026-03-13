package com.prabin.lbs.service;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.prabin.lbs.exception.GenreException;
import com.prabin.lbs.payload.dto.GenreDTO;



public interface GenreService {
	
	GenreDTO createGenreDTO(GenreDTO genreDTO);
	
	List<GenreDTO> getAllGenres();
	
	GenreDTO getGenreById(Long genreId) throws GenreException;
	
	GenreDTO updateGenre(Long genreId, GenreDTO genre) throws GenreException;
	
	void deleteGenre(Long genreId) throws GenreException;
	
	void hardDeleteGenre(Long genreId) throws Exception;
	
	List<GenreDTO> getAllActiveGenresWithSubGenres();
	
	List<GenreDTO> getTopLevelGenres();
	
//	Page<GenreDTO> searchGenres(String searchTerm, Pageable pageable);
	
	long getTotalActiveGenres();
	
	long getBookCountByGenre(Long genreId);
	
}
