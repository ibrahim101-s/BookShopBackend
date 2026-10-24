package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter @Setter
public class OrderItemEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;
    @Column(nullable = false)
    private String bookName;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Double price;
}
