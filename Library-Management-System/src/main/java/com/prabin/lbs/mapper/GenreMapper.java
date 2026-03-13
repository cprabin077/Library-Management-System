package com.prabin.lbs.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.prabin.lbs.modal.GenreModal;
import com.prabin.lbs.payload.dto.GenreDTO;
import com.prabin.lbs.repository.GenreRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GenreMapper {

	private final GenreRepository genreRepository;

	public GenreDTO toDTO(GenreModal savedGenre) {
		if (savedGenre == null) {
			return null;
		}

		GenreDTO dto = GenreDTO.builder().id(savedGenre.getId()).code(savedGenre.getCode()).name(savedGenre.getName())
				.description(savedGenre.getDescription()).displayOrder(savedGenre.getDisplayOrder())
				.active(savedGenre.getActive()).createdAt(savedGenre.getCreatedAt())
				.updatedAt(savedGenre.getUpdatedAt()).build();

		if (savedGenre.getParentGenre() != null) {
			dto.setParentGenreId(savedGenre.getParentGenre().getId());
			dto.setParentGenreName(savedGenre.getParentGenre().getName());
		}

		if (savedGenre.getSubGenres() != null && !savedGenre.getSubGenres().isEmpty()) {
			dto.setSubGenre(savedGenre.getSubGenres().stream().filter(subGenre -> subGenre.getActive())
					.map(subGenre -> toDTO(subGenre)).collect(Collectors.toList()));
		}

//		dto.setBookCount((long) (saveGenre.getBook));

		return dto;
	}

	public GenreModal toEntity(GenreDTO genreDTO) {
		if (genreDTO == null) {
			return null;

		}
		GenreModal genre = GenreModal.builder().code(genreDTO.getCode()).name(genreDTO.getName())
				.description(genreDTO.getDescription()).displayOrder(genreDTO.getDisplayOrder()).active(true)

				.build();

		if (genreDTO.getParentGenreId() != null) {
			genreRepository.findById(genreDTO.getParentGenreId()).ifPresent(genre::setParentGenre);
//			genre.setParentGenre(parentGenreModal);
		}
		return genre;
	}

	public void updateEntityFromDTO(GenreDTO dto, GenreModal existingGenre) {
		if(dto == null || existingGenre == null) {
			return ;
		}
		
		existingGenre.setCode(dto.getCode());
		existingGenre.setName(dto.getName());
		existingGenre.setDescription(dto.getDescription());
		existingGenre.setDisplayOrder(dto.getDisplayOrder() != null ? dto.getDisplayOrder(): 0);
		if(dto.getActive() != null) {
			existingGenre.setActive(dto.getActive());
		}
		if(dto.getParentGenreId() != null) {
			genreRepository.findById(dto.getParentGenreId())
			.ifPresent(existingGenre::setParentGenre);
		}
		
	}
	
	public List<GenreDTO> toDTOList(List<GenreModal> genreList){
		return genreList.stream().map(genre -> toDTO(genre)).collect(Collectors.toList());
	}
	
}
