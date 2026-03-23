package com.prabin.lbs.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabin.lbs.exception.UserException;
import com.prabin.lbs.payload.dto.UserDTO;
import com.prabin.lbs.payload.request.ForgotPasswordRequest;
import com.prabin.lbs.payload.request.LoginRequest;
import com.prabin.lbs.payload.request.ResetPasswordRequest;
import com.prabin.lbs.payload.response.ApiResponse;
import com.prabin.lbs.payload.response.AuthResponse;
import com.prabin.lbs.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signupHandler(@RequestBody @Valid UserDTO req) throws UserException {
		AuthResponse res = authService.signup(req);
		return ResponseEntity.ok(res);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody @Valid LoginRequest req) throws UserException {
		AuthResponse res = authService.login(req.getUsername(), req.getPassword());
		return ResponseEntity.ok(res);
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ForgotPasswordRequest req) throws UserException {
		authService.createPasswordResetToken(req.getEmail());
		ApiResponse res = new ApiResponse("A reset link was sent to your email", true);
		return ResponseEntity.ok(res);
	}
	
	public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest req) throws Exception{
		authService.resetPassword(req.getToken(), req.getPassword());
		ApiResponse res = new ApiResponse("Password reset successfully", true);
		
		return ResponseEntity.ok(res);
	}

}
