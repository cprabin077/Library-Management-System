package com.prabin.lbs.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prabin.lbs.modal.BookModal;

public interface BookRepository extends JpaRepository<BookModal, Long> {

	Optional<BookModal> findByIsbn(String isbn);

	boolean existsByIsbn(String isbn);
	
	@Query("""
			SELECT b FROM BookModal b 
			WHERE 
			(
			    :searchTerm IS NULL OR 
			    lower(b.title) LIKE lower(concat('%', :searchTerm, '%')) OR 
			    lower(b.author) LIKE lower(concat('%', :searchTerm, '%')) OR 
			    lower(b.isbn) LIKE lower(concat('%', :searchTerm, '%'))
			)
			AND (:genreId IS NULL OR b.genreModal.id = :genreId)
			AND (:availableOnly = false OR b.availableCopies > 0)
			AND b.active = true
			""")
			Page<BookModal> searchBookWithFilters(
			        @Param("searchTerm") String searchTerm,
			        @Param("genreId") Long genreId,
			        @Param("availableOnly") boolean availableOnly,
			        Pageable pageable
			);
	
	long countByActiveTrue();
	
	@Query("select count(b) from BookModal b where b.availableCopies > 0 AND b.active=true")
	long countAvailableBooks();

}
