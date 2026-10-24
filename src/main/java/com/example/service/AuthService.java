package com.example.service;

import com.example.dto.request.LoginRequestDTO;
import com.example.dto.response.LoginResponseDTO;
import com.example.entity.UserEntity;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserRepository;
import com.example.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    public LoginResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new LoginResponseDTO(jwtService.generateAccessToken(user.getUsername()), jwtService.generateRefreshToken(user.getUsername()));
    }
    public LoginResponseDTO refresh(String token) {
        if (token == null || token.isBlank()) throw new IllegalArgumentException("Refresh token is required");
        String username;
        try { username = jwtService.extractUsername(token); }
        catch (RuntimeException ex) { throw new IllegalArgumentException("Invalid refresh token"); }
        if (!jwtService.isRefreshTokenValid(token, username)) throw new IllegalArgumentException("Invalid refresh token");
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new LoginResponseDTO(jwtService.generateAccessToken(username), token);
    }
}
