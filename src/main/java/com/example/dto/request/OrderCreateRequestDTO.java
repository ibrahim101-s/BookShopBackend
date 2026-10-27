package com.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreateRequestDTO {
    @NotBlank(message = "Ünvan boş ola bilməz")
    private String address;

    @NotBlank(message = "Kart nömrəsi boş ola bilməz")
    private String cardNumber;

    @NotEmpty(message = "Sifariş üçün ən az bir kitab seçilməlidir")
    private List<Long> bookIds;
}