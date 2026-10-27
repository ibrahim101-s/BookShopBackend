package com.example.service;

import com.example.dto.request.UserLoginRequestDTO;
import com.example.dto.request.UserRegisterRequestDTO;
import com.example.dto.response.AuthResponseDTO;
import com.example.entity.UserEntity;
import com.example.exception.AlreadyExistsException;
import com.example.exception.NotFoundException;
import com.example.repository.UserRepository;
import com.example.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public String register(UserRegisterRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AlreadyExistsException("Bu istifadəçi adı artıq mövcuddur!");
        }

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .surname(request.getSurname())
                .address(request.getAddress())
                .build();

        userRepository.save(user);
        return "İstifadəçi uğurla qeydiyyatdan keçdi";
    }

    public AuthResponseDTO login(UserLoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new NotFoundException("İstifadəçi adı və ya şifrə yanlışdır!");
        }

        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponseDTO(token);
    }
}