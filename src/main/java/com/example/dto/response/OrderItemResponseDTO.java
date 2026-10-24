package com.example.dto.response;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class OrderItemResponseDTO {
    private Long bookId; private String bookName; private Integer quantity; private Double price;
}
