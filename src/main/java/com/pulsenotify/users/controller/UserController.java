package com.pulsenotify.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulsenotify.response.ApiResponse;
import com.pulsenotify.users.dto.RegistrationRequest;
import com.pulsenotify.users.dto.RegistrationResponse;
import com.pulsenotify.users.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/")
public class UserController {
    private final UserService userservice;
    public UserController(UserService userservice) {
        this.userservice = userservice;
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegistrationResponse>> registerUser(@Valid @RequestBody RegistrationRequest request){
        RegistrationResponse response = userservice.registerUser(request);
        ApiResponse<RegistrationResponse> apiResponse = new ApiResponse<>(true, "User Registered", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
