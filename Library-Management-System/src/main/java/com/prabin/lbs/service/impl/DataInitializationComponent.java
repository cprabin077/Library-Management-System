package com.prabin.lbs.service.impl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.prabin.lbs.domain.UserRole;
import com.prabin.lbs.modal.UserModal;
import com.prabin.lbs.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner{
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) {
		initializeAdminUser();
	}
	
	private void initializeAdminUser() {
		String adminEmail = "prabin@gmail.com";
		String adminPassword = "prabin";
		
		if(userRepository.findByEmail(adminEmail)==null) {
			UserModal userModal = UserModal.builder()
					.password(passwordEncoder.encode(adminPassword))
					.email(adminEmail)
					.fullName("Prabin Chaudhary")
					.role(UserRole.ROLE_ADMIN)
					.build();
			
			UserModal admin = userRepository.save(userModal);
		}
	}

}
