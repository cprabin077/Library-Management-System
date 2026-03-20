package com.prabin.lbs.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.prabin.lbs.modal.UserModal;
import com.prabin.lbs.payload.dto.UserDTO;

@Service
public class UserMapper {
	
	public static UserDTO toDTO(UserModal userModal) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(userModal.getId());
		userDTO.setEmail(userModal.getEmail());
		userDTO.setFullName(userModal.getFullName());
		userDTO.setPhone(userModal.getPhone());
		userDTO.setLastLogin(userModal.getLastLogin());
		userDTO.setRole(userModal.getRole());
		
		return userDTO;
	}
	
	public static List<UserDTO> toDTOList(List<UserModal> users){
		return users.stream()
			.map(UserMapper::toDTO)
			.collect(Collectors.toList());
	}
	
	public static Set<UserDTO> toDTOSet(Set<UserModal> users){
		return users.stream()
				.map(UserMapper::toDTO)
				.collect(Collectors.toSet());
	}
	
	public static UserModal toEntity(UserDTO userDTO) {
		UserModal createdUser = new UserModal();
		createdUser.setEmail(userDTO.getEmail());
		createdUser.setPassword(userDTO.getPassword());
		createdUser.setCreatedAt(LocalDateTime.now());
		createdUser.setPhone(userDTO.getPhone());
		createdUser.setFullName(userDTO.getFullName());
		createdUser.setRole(userDTO.getRole());
		
		return createdUser;
	}
	
	

}
