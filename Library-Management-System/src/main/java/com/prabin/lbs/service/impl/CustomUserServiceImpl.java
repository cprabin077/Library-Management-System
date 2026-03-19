package com.prabin.lbs.service.impl;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prabin.lbs.modal.UserModal;
import com.prabin.lbs.repository.UserRepository;

@Service
public class CustomUserServiceImpl implements UserDetailsService{

	private final UserRepository userRepository;
	
	public CustomUserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserModal user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not exist with username: "+ username);
		}
		
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
		
		Collection<? extends GrantedAuthority> authorities = Collections.singletonList(authority);
		
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), user.getPassword(), authorities);
		
		
	}
}
