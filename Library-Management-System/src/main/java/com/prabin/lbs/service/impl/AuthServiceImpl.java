package com.prabin.lbs.service.impl;

import java.time.LocalDateTime;

import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prabin.lbs.configurations.JwtProvider;
import com.prabin.lbs.domain.UserRole;
import com.prabin.lbs.exception.UserException;
import com.prabin.lbs.mapper.UserMapper;
import com.prabin.lbs.modal.PasswordResetToken;
import com.prabin.lbs.modal.UserModal;
import com.prabin.lbs.payload.dto.UserDTO;
import com.prabin.lbs.payload.response.AuthResponse;
import com.prabin.lbs.repository.PasswordResetTokenRepository;
import com.prabin.lbs.repository.UserRepository;
import com.prabin.lbs.service.AuthService;
import com.prabin.lbs.service.EmailService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordResetTokenRepository passwordResetTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final CustomUserServiceImpl customUserServiceImpl;
	private final EmailService emailService;
	// private final UserMapper userMapper;

	@Override
	public AuthResponse login(String username, String password) throws UserException {
		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
//		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//		String role = authorities.iterator().next().getAuthority();
		String token = jwtProvider.generateToken(authentication);

		UserModal userModal = userRepository.findByEmail(username);

		// update last login
		userModal.setLastLogin(LocalDateTime.now());
		userRepository.save(userModal);

		AuthResponse response = new AuthResponse();
		response.setTitle("Login Success");
		response.setMessage("welcome Back" + username);
		response.setJwt(token);
		response.setUser(UserMapper.toDTO(userModal));

		return response;
	}

	private Authentication authenticate(String username, String password) throws UserException {

	    UserDetails userDetails = customUserServiceImpl.loadUserByUsername(username);

	    if (userDetails == null) {
	        throw new UserException("User not found with email - " + username);
	    }

	    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
	        throw new UserException("Password not matched");
	    }

	    return new UsernamePasswordAuthenticationToken(
	            userDetails,
	            null,
	            userDetails.getAuthorities()
	    );
	}

	@Override
	public AuthResponse signup(UserDTO req) throws UserException {
		UserModal user = userRepository.findByEmail(req.getEmail());

		if (user != null) {
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

		Authentication auth = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());

		SecurityContextHolder.getContext().setAuthentication(auth);

		String jwt = jwtProvider.generateToken(auth);

		AuthResponse response = new AuthResponse();
		response.setJwt(jwt);
		response.setTitle("Welcome " + createdUser.getFullName());
		response.setMessage("Registered successfully");
		response.setUser(UserMapper.toDTO(savedUser));

		return response;
	}

	@Transactional
	public void createPasswordResetToken(String email) throws UserException {

		String frontendUrl = "http://localhost:5173";
		UserModal userModal = userRepository.findByEmail(email);

		if (userModal == null) {
			throw new UserException("User not found with given email");
		}

		String token = UUID.randomUUID().toString();

		PasswordResetToken resetToken = PasswordResetToken.builder().token(token).userModal(userModal)
				.expiryDate(LocalDateTime.now().plusMinutes(5)).build();

		passwordResetTokenRepository.save(resetToken);
		String resetLink = frontendUrl + token;
		String subject = "Password Reset Request";
		String body = "You requested to reset your password. Use this link(valid for 5 minutes only):" + resetLink;

		// Sent email
		emailService.sendEmail(userModal.getEmail(), subject, body);
	}

	@Transactional
	public void resetPassword(String token, String newPassword) throws Exception {
		PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
				.orElseThrow(() -> new Exception("token not valid"));
		
		if (resetToken.isExpired()) {
			passwordResetTokenRepository.delete(resetToken);
			throw new Exception("Token expired");
		}

		UserModal user = resetToken.getUserModal();
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		passwordResetTokenRepository.delete(resetToken);
	}

}
