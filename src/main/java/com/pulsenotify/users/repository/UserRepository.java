package com.pulsenotify.users.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pulsenotify.users.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,UUID>{

}
		