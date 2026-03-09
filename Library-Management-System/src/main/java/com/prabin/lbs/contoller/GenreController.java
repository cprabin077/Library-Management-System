package com.prabin.lbs.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabin.lbs.modal.GenreModal;
import com.prabin.lbs.payload.dto.GenreDTO;
import com.prabin.lbs.service.GenreService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {
	
	private final GenreService genreService;
	
	@PostMapping("/create")
	public ResponseEntity<GenreModal> addGenre (@RequestBody GenreModal genreModal){
		// GenreDTO createdGenre = genreService.createGenreModal(genreModal);
		// return ResponseEntity.ok(createdGenre);
		return null;
	}

}
