package com.prabin.lbs.mapper;

import java.util.List;
import java.util.stream.Collectors;

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
	
	

}
