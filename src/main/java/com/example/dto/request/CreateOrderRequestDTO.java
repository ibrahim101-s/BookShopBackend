package com.example.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class CreateOrderRequestDTO {
    @NotBlank(message = "Address is required") private String address;
}
