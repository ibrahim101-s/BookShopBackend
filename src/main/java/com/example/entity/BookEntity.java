package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = "barcode"))
@Getter @Setter
public class BookEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private Integer quantity;
    @Column(name = "page_count", nullable = false)
    private Integer pageCount;
    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false, unique = true)
    private String barcode;
    private String imageUrl;
}
