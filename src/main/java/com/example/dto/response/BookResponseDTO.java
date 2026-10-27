package com.example.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookResponseDTO {
    private Long id;
    private String name;
    private String author;
    private Integer quantity;
    private Integer pageCount;
    private LocalDate publishDate;
    private String username;
    private Double price;
    private String barcode;
    private String imageUrl;
    private String createdBy;
}