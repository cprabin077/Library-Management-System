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

import com.prabin.lbs.exception.BookException;
import com.prabin.lbs.payload.dto.BookDTO;
import com.prabin.lbs.payload.request.BookSearchRequest;
import com.prabin.lbs.payload.response.ApiResponse;
import com.prabin.lbs.payload.response.PageResponse;
import com.prabin.lbs.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

	@PostMapping("/bulk")
	public ResponseEntity<?> createBooksBulk(@Valid @RequestBody List<BookDTO> bookDTOS) throws BookException {
		List<BookDTO> createdBooks = bookService.createBooksBulk(bookDTOS);
		return ResponseEntity.ok(createdBooks);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) throws BookException {
		BookDTO book = bookService.getBookById(id);
		return ResponseEntity.ok(book);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO)
			throws BookException {
		BookDTO updatedBook = bookService.updateBook(id, bookDTO);
		return ResponseEntity.ok(updatedBook);
	}

	// Soft delete book i.e. marks as inactive
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long id) throws BookException {
		bookService.deleteBook(id);
		return ResponseEntity.ok(new ApiResponse("Book deleted successfully", true));
	}

	// Hard delete book
	@DeleteMapping("/{id}/permanent")
	public ResponseEntity<ApiResponse> hardDeleteBook(@PathVariable Long id) throws BookException {
		bookService.hardDeleteBook(id);
		return ResponseEntity.ok(new ApiResponse("Book deleted permanently", true));
	}

	@GetMapping
	public ResponseEntity<PageResponse<BookDTO>> searchBooks(@RequestParam(required = false) Long genreId,
			@RequestParam(required = false) Boolean availableOnly,
			@RequestParam(defaultValue = "true") boolean activeOnly, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "createdAt") String sortBy,
			@RequestParam(defaultValue = "DESC") String sortDirection) {
		// Build search request from query parameters
		BookSearchRequest searchRequest = new BookSearchRequest();
		searchRequest.setGenreId(genreId);
		searchRequest.setAvailableOnly(availableOnly);
		searchRequest.setPage(page);
		searchRequest.setSize(size);
		searchRequest.setSortBy(sortBy);
		searchRequest.setSortDirection(sortDirection);

		PageResponse<BookDTO> books = bookService.searchBooksWithFilters(searchRequest);
		return ResponseEntity.ok(books);
	}

	// search
	@PostMapping("/search")
	public ResponseEntity<PageResponse<BookDTO>> advancedSearch(@RequestBody BookSearchRequest searchRequest) {
		PageResponse<BookDTO> books = bookService.searchBooksWithFilters(searchRequest);
		return ResponseEntity.ok(books);
	}

	@GetMapping("/stats")
	public ResponseEntity<BookStatsResponse> getBookStats() {
		long totalActive = bookService.getTotalActiveBooks();
		long totalAvailable = bookService.getTotalAvailableBooks();

		BookStatsResponse stats = new BookStatsResponse(totalActive, totalAvailable);
		return ResponseEntity.ok(stats);
	}

	public static class BookStatsResponse {
		public long totalActiveBooks;
		public long totalAvailableBooks;

		public BookStatsResponse(long totalActiveBooks, long totalAvailableBooks) {
			this.totalActiveBooks = totalActiveBooks;
			this.totalAvailableBooks = totalAvailableBooks;

		}
	}

}
