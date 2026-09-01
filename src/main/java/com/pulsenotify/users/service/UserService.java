package com.pulsenotify.users.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pulsenotify.exception.EmailAlreadyExistsException;
import com.pulsenotify.users.dto.RegistrationRequest;
import com.pulsenotify.users.dto.RegistrationResponse;
import com.pulsenotify.users.entity.User;
import com.pulsenotify.users.repository.UserRepository;

@Service
public class UserService {

    public final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public RegistrationResponse registerUser(RegistrationRequest request){
        String email = request.getEmail().trim().toLowerCase();
        if(userRepository.existsByEmail(email))
            throw new EmailAlreadyExistsException(email);
        User user = new User(email);
        User result = userRepository.save(user);
        return new RegistrationResponse(result.getId() , result.getEmail());
    }
}
