package com.prabin.lbs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prabin.lbs.payload.response.ApiResponse;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(GenreException.class)
	public ResponseEntity<ApiResponse> handleGenreException(GenreException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiResponse(e.getMessage(),false));
	}

}
