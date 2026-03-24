package com.prabin.lbs.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabin.lbs.exception.BookException;
import com.prabin.lbs.payload.dto.BookDTO;
import com.prabin.lbs.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/books")
public class AdminController {
	private final BookService bookService;

	@PostMapping()
	public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) throws BookException {
		BookDTO createdBook = bookService.createBook(bookDTO);
		return ResponseEntity.ok(createdBook);
	}


}
