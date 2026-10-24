package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart_items", uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "book_id"}))
@Getter @Setter
public class CartItemEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;
    @Column(nullable = false)
    private Integer quantity;
}
