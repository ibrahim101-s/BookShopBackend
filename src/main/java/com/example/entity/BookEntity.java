package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String author;
    private Integer quantity;
    private Integer pageCount;
    private LocalDate publishDate;
    private String username;
    private Double price;

    @Column(unique = true)
    private String barcode;

    private String imageUrl;
    private String createdBy;
}