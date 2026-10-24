package com.example.dto.response;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter @Setter
public class CartResponseDTO {
    private Long id; private List<CartItemResponseDTO> items; private Double totalPrice;
}
