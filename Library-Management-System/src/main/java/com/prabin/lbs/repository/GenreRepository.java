package com.prabin.lbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prabin.lbs.modal.GenreModal;

public interface GenreRepository extends JpaRepository<GenreModal, Long> {

	GenreModal save(GenreModal genreModal);

	List<GenreModal> findByActiveTrueOrderByDisplayOrderAsc();

	List<GenreModal> findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();

	List<GenreModal> findByParentGenreIdAndActiveTrueOrderByDisplayOrderAsc(Long parentGenreId);

	long countByActiveTrue();

//	@Query("select count(b) from book b where b.genre.id=:genreId")
//	long countBooksByGenre(@Param("genreId") Long genreId);

}
