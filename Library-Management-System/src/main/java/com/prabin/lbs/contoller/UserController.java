package com.prabin.lbs.contoller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabin.lbs.modal.UserModal;
import com.prabin.lbs.payload.dto.UserDTO;
import com.prabin.lbs.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;
	
	@GetMapping("/list")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		return ResponseEntity.ok(
				userService.getAllUsers()
				);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<UserModal> getUserProfile() throws Exception{
		return ResponseEntity.ok(
				userService.getCurrentUser()
				);
	}
}
