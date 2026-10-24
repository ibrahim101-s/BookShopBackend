package com.example.controller;
import com.example.dto.request.LoginRequestDTO;
import com.example.dto.request.RefreshTokenRequestDTO;
import com.example.dto.request.RegisterRequestDTO;
import com.example.dto.response.LoginResponseDTO;
import com.example.service.AuthService;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/users") @RequiredArgsConstructor
public class AuthController {
    private final UserService userService; private final AuthService authService;
    @PostMapping("/register") @Operation(summary = "Register user", description = "Creates a user account")
    public String register(@Valid @RequestBody RegisterRequestDTO request) { return userService.register(request); }
    @PostMapping("/login") @Operation(summary = "Login", description = "Returns access and refresh tokens")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO request) { return authService.login(request); }
    @PostMapping("/refresh") @Operation(summary = "Refresh access token", description = "Returns a new access token using a valid refresh token")
    public LoginResponseDTO refresh(@Valid @RequestBody RefreshTokenRequestDTO request) { return authService.refresh(request.getRefreshToken()); }
}
