package com.prabin.lbs.service;

import com.prabin.lbs.payload.dto.UserDTO;
import com.prabin.lbs.payload.response.AuthResponse;

public interface AuthService {
	
	AuthResponse login(String username, String password);
	AuthResponse signup(UserDTO req);

	void createPasswordResetToken(String email);
	void resetPassword(String token, String newPassword);
	
	
	

}
