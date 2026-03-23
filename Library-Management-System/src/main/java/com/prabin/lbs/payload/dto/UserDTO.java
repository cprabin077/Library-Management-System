package com.prabin.lbs.payload.dto;

import java.time.LocalDateTime;

import com.prabin.lbs.domain.UserRole;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private Long id;
	
	@NotNull(message = "Email is required")
	private String email;
	
	@NotNull(message = "Password is required")
	private String password;
	private String phone;
	
	@NotNull(message = "Full Name is required")
	private String fullName;
	
	private UserRole role;
	
	
	private LocalDateTime lastLogin;

}

