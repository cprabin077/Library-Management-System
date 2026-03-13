package com.prabin.lbs.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.prabin.lbs.exception.GenreException;
import com.prabin.lbs.mapper.GenreMapper;
import com.prabin.lbs.modal.GenreModal;
import com.prabin.lbs.payload.dto.GenreDTO;
import com.prabin.lbs.repository.GenreRepository;
import com.prabin.lbs.service.GenreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

	private final GenreRepository genreRepository;
	private final GenreMapper genreMapper;

	@Override
	public GenreDTO createGenreDTO(GenreDTO genreDTO) {

		// return genreRepository.save(genreDTO);
		GenreModal genre = genreMapper.toEntity(genreDTO);
		GenreModal savedGenre = genreRepository.save(genre);

		return genreMapper.toDTO(savedGenre);
	}

	@Override
	public List<GenreDTO> getAllGenres() {

		return genreRepository.findAll().stream().map(genre -> genreMapper.toDTO(genre)).collect(Collectors.toList());
	}

	@Override
	public GenreDTO getGenreById(Long genreId) throws GenreException {

		GenreModal genre = genreRepository.findById(genreId).orElseThrow(
				() -> new GenreException("genre not found"));

		return genreMapper.toDTO(genre);
	}

	@Override
	public GenreDTO updateGenre(Long genreId, GenreDTO genreDTO) throws GenreException {
		GenreModal existingGenre = genreRepository.findById(genreId).orElseThrow(
				()-> new GenreException("Genre not found")
		);
		
		genreMapper.updateEntityFromDTO(genreDTO, existingGenre);
		GenreModal updatedGenre =  genreRepository.save(existingGenre);
		return genreMapper.toDTO(updatedGenre);
	}

	@Override
	public void deleteGenre(Long genreId) throws GenreException {
		GenreModal existingGenre = genreRepository.findById(genreId).orElseThrow(
				()-> new GenreException("Genre not found")
		);
		existingGenre.setActive(false);
		genreRepository.save(existingGenre);
	}

	@Override
	public void hardDeleteGenre(Long genreId) throws GenreException {
		GenreModal existingGenre = genreRepository.findById(genreId).orElseThrow(
				()-> new GenreException("Genre not found")
		);
		genreRepository.delete(existingGenre);


	}

	@Override
	public List<GenreDTO> getAllActiveGenresWithSubGenres() {
		List<GenreModal> topLevelGenres = genreRepository
				.findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();
		return genreMapper.toDTOList(topLevelGenres);
	}

	@Override
	public List<GenreDTO> getTopLevelGenres() {
		List<GenreModal> topLevelGenres = genreRepository
				.findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();
		return genreMapper.toDTOList(topLevelGenres);
	}

	@Override
	public long getTotalActiveGenres() {
		return genreRepository.countByActiveTrue();
	}

	@Override
	public long getBookCountByGenre(Long genreId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
