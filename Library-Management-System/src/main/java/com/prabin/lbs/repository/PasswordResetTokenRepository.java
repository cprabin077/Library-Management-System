package com.prabin.lbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prabin.lbs.modal.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

}
