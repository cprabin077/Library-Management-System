package com.prabin.lbs.service;

import java.util.List;

import com.prabin.lbs.modal.UserModal;
import com.prabin.lbs.payload.dto.UserDTO;

public interface UserService {
	
	public UserModal getCurrentUser() throws Exception;
	
	public List<UserDTO> getAllUsers();

}
