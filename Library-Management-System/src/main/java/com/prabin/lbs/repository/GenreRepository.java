package com.prabin.lbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prabin.lbs.modal.GenreModal;

public interface GenreRepository extends JpaRepository<GenreModal, Long>{

	GenreModal save(GenreModal genreModal);

}
