package com.prabin.lbs.modal;

import java.time.LocalDateTime;

import com.prabin.lbs.domain.AuthProvider;
import com.prabin.lbs.domain.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String token;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserModal userModal;
	
	@Column(nullable = false)
	private LocalDateTime expiryDate;
	
	public boolean isExpired() {
		return expiryDate.isBefore(LocalDateTime.now());
	}

}
