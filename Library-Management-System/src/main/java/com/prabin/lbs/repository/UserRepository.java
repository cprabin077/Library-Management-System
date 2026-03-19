package com.prabin.lbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prabin.lbs.modal.UserModal;

public interface UserRepository extends JpaRepository<UserModal, Long> {
	
	UserModal findByEmail(String email);

}
