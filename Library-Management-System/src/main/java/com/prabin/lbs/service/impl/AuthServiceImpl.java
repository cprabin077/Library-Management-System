package com.prabin.lbs.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prabin.lbs.configurations.JwtProvider;
import com.prabin.lbs.domain.UserRole;
import com.prabin.lbs.exception.UserException;
import com.prabin.lbs.modal.UserModal;
import com.prabin.lbs.payload.dto.UserDTO;
import com.prabin.lbs.payload.response.AuthResponse;
import com.prabin.lbs.repository.UserRepository;
import com.prabin.lbs.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public AuthResponse login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthResponse signup(UserDTO req) throws UserException {
		UserModal user = userRepository.findByEmail(req.getEmail());

		if (user == null) {
			throw new UserException("Email is already registered");
		}

		UserModal createdUser = new UserModal();
		createdUser.setEmail(req.getEmail());
		createdUser.setPassword(passwordEncoder.encode(req.getPassword()));
		createdUser.setPhone(req.getPhone());
		createdUser.setFullName(req.getFullName());
		createdUser.setLastLogin(LocalDateTime.now());
		createdUser.setRole(UserRole.ROLE_USER);

		UserModal savedUser = userRepository.save(createdUser);

		Authentication auth = new UsernamePasswordAuthenticationToken(
				savedUser.getEmail(), savedUser.getPassword());

		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String jwt = jwtProvider.generateToken(auth);
		
		AuthResponse response = new AuthResponse();
		response.setJwt(jwt);
		response.setTitle("Welcome "+ createdUser.getFullName());
		response.setMessage("Registered successfully");
		response.setUser(user);
		return null;
	}

	@Override
	public void createPasswordResetToken(String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetPassword(String token, String newPassword) {
		// TODO Auto-generated method stub

	}

}
