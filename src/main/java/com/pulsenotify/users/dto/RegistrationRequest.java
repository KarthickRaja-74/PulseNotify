package com.pulsenotify.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegistrationRequest {

    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String password;

    public String getEmail(){
        return this.email;
    }
    
    public String getPassword(){
		return this.password;
	}

    public void setEmail(String email){
        this.email = email;
    }
}
