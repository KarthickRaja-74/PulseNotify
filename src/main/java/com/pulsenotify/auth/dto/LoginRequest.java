package com.pulsenotify.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginRequest {
	
	@Email
	@NotBlank
	private String email;
	@NotBlank
	@NotNull
	private String password;
	
    public String getEmail(){
        return this.email;
    }
    
    public String getPassword(){
		return this.password;
	}
}
