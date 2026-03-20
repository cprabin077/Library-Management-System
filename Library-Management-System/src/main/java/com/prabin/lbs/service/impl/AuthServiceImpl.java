package com.prabin.lbs.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prabin.lbs.configurations.JwtProvider;
import com.prabin.lbs.domain.UserRole;
import com.prabin.lbs.exception.UserException;
import com.prabin.lbs.mapper.UserMapper;
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
	private final JwtProvider jwtProvider;
	private final CustomUserServiceImpl customUserServiceImpl;
	// private final UserMapper userMapper;

	@Override
	public AuthResponse login(String username, String password) throws UserException {
		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
//		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//		String role = authorities.iterator().next().getAuthority();
		String token = jwtProvider.generateToken(authentication);
		
		UserModal userModal = userRepository.findByEmail(username);
		
		//update last login
		userModal.setLastLogin(LocalDateTime.now());
		userRepository.save(userModal);
		
		AuthResponse response = new AuthResponse();
		response.setTitle("Login Success");
		response.setMessage("welcome Back"+ username);
		response.setJwt(token);
		response.setUser(UserMapper.toDTO(userModal));
		
		return response;
	}

	private Authentication authenticate(String username, String password) throws UserException {
		UserDetails userDetails = customUserServiceImpl.loadUserByUsername(username);
		
		if(userDetails == null) {
			throw new UserException("User not found with email - "+ password);
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new UserException("Password not matched");
		}
		return new UsernamePasswordAuthenticationToken(password, userDetails.getPassword());
	
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
		response.setUser(UserMapper.toDTO(savedUser));
		
		return response;
	}

	@Override
	public void createPasswordResetToken(String email) {
		
	}

	@Override
	public void resetPassword(String token, String newPassword) {
		// TODO Auto-generated method stub

	}

}
