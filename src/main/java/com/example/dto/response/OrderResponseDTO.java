package com.example.dto.response;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
@Getter @Setter
public class OrderResponseDTO {
    private Long id; private String username; private String address; private Double totalPrice;
    private LocalDateTime createdAt; private List<OrderItemResponseDTO> items;
}
