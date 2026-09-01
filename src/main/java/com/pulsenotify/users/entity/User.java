package com.pulsenotify.users.entity;

import com.pulsenotify.common.AuditedEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends AuditedEntity {
	private String email;
    public User() {
    }
    public User(String email){
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
