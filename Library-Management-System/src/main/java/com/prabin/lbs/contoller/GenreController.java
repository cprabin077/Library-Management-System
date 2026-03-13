package com.prabin.lbs.contoller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prabin.lbs.exception.GenreException;
import com.prabin.lbs.payload.dto.GenreDTO;
import com.prabin.lbs.payload.response.ApiResponse;
import com.prabin.lbs.service.GenreService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

	private final GenreService genreService;

	@PostMapping("/create")
	public ResponseEntity<GenreDTO> addGenre(@RequestBody GenreDTO genreDTO) {
		GenreDTO createdGenre = genreService.createGenreDTO(genreDTO);
		return ResponseEntity.ok(createdGenre);

	}

	@GetMapping()
	public ResponseEntity<?> getAllGenres() {
		List<GenreDTO> genres = genreService.getAllGenres();
		return ResponseEntity.ok(genres);

	}

	@GetMapping("/{genreId}")
	public ResponseEntity<?> getGenreById(@PathVariable("genreId") Long genreId) throws GenreException {
		GenreDTO genres = genreService.getGenreById(genreId);
		return ResponseEntity.ok(genres);

	}

	@PutMapping("/{genreId}")
	public ResponseEntity<?> updateGenre(@PathVariable("genreId") Long genreId, @RequestBody GenreDTO genreDTO)
			throws GenreException {
		GenreDTO genres = genreService.updateGenre(genreId, genreDTO);
		return ResponseEntity.ok(genres);

	}

	@DeleteMapping("/{genreId}")
	public ResponseEntity<?> deleteGenre(@PathVariable("genreId") Long genreId) throws GenreException {
		genreService.deleteGenre(genreId);
		ApiResponse response = new ApiResponse("Genre soft deletd successfully", true);
		return ResponseEntity.ok(response);

	}

	@DeleteMapping("/{genreId}/hard")
	public ResponseEntity<?> hardDeleteGenre(@PathVariable("genreId") Long genreId) throws Exception {
		genreService.hardDeleteGenre(genreId);
		ApiResponse response = new ApiResponse("Genre hard deletd successfully", true);
		return ResponseEntity.ok(response);

	}

	@GetMapping("/top-level")
	public ResponseEntity<?> getTopLevelGenres() {
		List<GenreDTO> genres = genreService.getTopLevelGenres();
		return ResponseEntity.ok(genres);

	}
	
	@GetMapping("/count")
	public ResponseEntity<?> getTotalActiveGenres() {
		Long genres = genreService.getTotalActiveGenres();
		return ResponseEntity.ok(genres);

	}
	
	@GetMapping("/{id}/book-count")
	public ResponseEntity<?> getBookCountByGenre(@PathVariable Long id) {
		Long count = genreService.getBookCountByGenre(id);
		
		return ResponseEntity.ok(count);

	}
}
