package com.prabin.lbs.service.impl;

import org.springframework.stereotype.Service;

import com.prabin.lbs.modal.GenreModal;
import com.prabin.lbs.payload.dto.GenreDTO;
import com.prabin.lbs.repository.GenreRepository;
import com.prabin.lbs.service.GenreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
	
	
	private final GenreRepository genreRepository;
	
	
	
	@Override
	public GenreDTO createGenreModal(GenreDTO genreDTO) {
		
		// return genreRepository.save(genreDTO);
		GenreModal genre = GenreModal.builder()
				.code(genreDTO.getCode())
				.name(genreDTO.getName())
				.description(genreDTO.getDescription())
				.displayOrder(genreDTO.getDisplayOrder())
				.active(true)
				
				.build();
		
		if(genreDTO.getParentGenreId() != null) {
			GenreModal parentGenreModal = genreRepository.findById(genreDTO.getParentGenreId()).get();
			genre.setParentGenre(parentGenreModal);
		}
		GenreModal saveGenre =  genreRepository.save(genre);
		
		GenreDTO dto = GenreDTO.builder()
				.id(saveGenre.getId())
				.code(saveGenre.getCode())
				.name(saveGenre.getName())
				.description(saveGenre.getDescription())
				.displayOrder(genreDTO.getDisplayOrder())
				.active(saveGenre.getActive())
				.createdAt(saveGenre.getCreatedAt())
				.updatedAt(saveGenre.getUpdatedAt())
				.build();
		
		if(saveGenre.getParentGenre() != null) {
			dto.setParentGenreId(saveGenre.getParentGenre().getId());
			dto.setParentGenreName(saveGenre.getParentGenre().getName());
		}
		
		return null;
	}



}
