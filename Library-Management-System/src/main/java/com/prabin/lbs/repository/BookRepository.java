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
	
	@Query(
			"select b from BookModal b where " + 
			":searchTerm is null OR"+
			"lower(b.title) like lower(concat('%', :searchTerm, '%')) OR "+
			"lower(b.author) like lower(concat('%', :searchTerm, '%')) OR "+
			"lower(b.isbn) like lower(concat('%', :searchTerm, '%')) OR "+
			"(:genreId is null or b.genre.id=genreId) AND " + 
			"(:availableOnly== fasle OR b.availableCopies > 0) AND " + 
			"b.active=true"
			)
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
