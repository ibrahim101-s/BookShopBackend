package com.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequestDTO {
    @NotBlank(message = "Username boş ola bilməz")
    private String username;

    @NotBlank(message = "Şifrə boş ola bilməz")
    @Size(min = 6, message = "Şifrə minimum 6 simvol olmalıdır")
    private String password;

    @NotBlank(message = "Ad boş ola bilməz")
    private String name;

    private String surname;
    private String address;
}