package com.prabin.lbs.service;

import com.prabin.lbs.exception.UserException;
import com.prabin.lbs.payload.dto.UserDTO;
import com.prabin.lbs.payload.response.AuthResponse;

public interface AuthService {
	
	AuthResponse login(String username, String password) throws UserException;
	AuthResponse signup(UserDTO req) throws UserException;

	void createPasswordResetToken(String email) throws UserException;
	void resetPassword(String token, String newPassword);
	
	
	

}
