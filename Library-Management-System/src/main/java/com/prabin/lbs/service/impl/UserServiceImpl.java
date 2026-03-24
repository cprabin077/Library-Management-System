package com.prabin.lbs.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.prabin.lbs.mapper.UserMapper;
import com.prabin.lbs.modal.UserModal;
import com.prabin.lbs.payload.dto.UserDTO;
import com.prabin.lbs.repository.UserRepository;
import com.prabin.lbs.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;

	@Override
	public UserModal getCurrentUser() throws Exception {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		UserModal userModal = userRepository.findByEmail(email); 
		
		if(userModal == null) {
			throw new Exception("User not found");
		}
		return userModal;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<UserModal> users = userRepository.findAll();
		return users.stream().map(
				UserMapper::toDTO				
				).collect(Collectors.toList());
	}

}
