package com.example.service;

import com.example.dto.request.RegisterRequestDTO;
import com.example.entity.UserEntity;
import com.example.exception.UsernameAlreadyExistsException;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(RegisterRequestDTO request) {
        String username = request.getUsername().trim();
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName().trim());
        user.setSurname(request.getSurname().trim());
        user.setAddress(request.getAddress());
        userRepository.save(user);
        return "User registered successfully";
    }
}
