package com.pulsenotify.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulsenotify.auth.dto.LoginRequest;
import com.pulsenotify.auth.dto.LoginResponse;
import com.pulsenotify.auth.service.AuthService;
import com.pulsenotify.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
	private final AuthService authService;
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
           @Valid @RequestBody LoginRequest request) {

        String token = authService.authenticateUser(request);
        LoginResponse response = new LoginResponse("Authentication successful" , token);
        return ResponseEntity.ok(new ApiResponse<>(true ,"Login successful", response));
    }
}
