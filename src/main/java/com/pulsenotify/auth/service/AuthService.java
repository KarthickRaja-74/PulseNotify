package com.pulsenotify.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pulsenotify.auth.dto.LoginRequest;
import com.pulsenotify.exception.AuthenticationException;
import com.pulsenotify.users.entity.User;
import com.pulsenotify.users.repository.UserRepository;

@Service
public class AuthService {
	
	public final UserRepository userRepository;
	public final PasswordEncoder passwordEncoder;
	
	public AuthService(UserRepository userRepository , PasswordEncoder passwordEncoder ) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public void authenticateUser(LoginRequest request) {
		String email = request.getEmail().trim().toLowerCase();
		User user = userRepository.findByEmail(email).orElseThrow(()-> new AuthenticationException("Invalid Email or Password"));
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new AuthenticationException("Invalid Email or Password");
		}
		
	}
}
