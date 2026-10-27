package com.example.controller;

import com.example.dto.response.AuthResponseDTO;
import com.example.dto.request.UserLoginRequestDTO;
import com.example.dto.request.UserRegisterRequestDTO;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Qeydiyyat və Login əməliyyatları")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Qeydiyyat", description = "Yeni istifadəçi qeydiyyatdan keçir")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterRequestDTO request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "JWT Token almaq üçün istifadə olunur")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody UserLoginRequestDTO request) {
        return ResponseEntity.ok(userService.login(request));
    }
}