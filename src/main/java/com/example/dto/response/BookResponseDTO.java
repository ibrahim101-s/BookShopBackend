package com.example.dto.response;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
@Getter @Setter
public class BookResponseDTO {
    private Long id; private String name; private String author; private Integer quantity;
    private Integer pageCount; private LocalDate publishDate; private String username;
    private Double price; private String barcode; private String imageUrl;
}
