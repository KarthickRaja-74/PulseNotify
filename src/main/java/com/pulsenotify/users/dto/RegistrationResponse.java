package com.pulsenotify.users.dto;
import java.util.UUID;

public class RegistrationResponse {
    private UUID id;
    private String email;

    public UUID getId(){
        return this.id;
    }

    public RegistrationResponse(UUID id , String email){
        this.id = id;
        this.email = email;
    }
    public void setId(UUID id){
        this.id = id;
    }
    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
