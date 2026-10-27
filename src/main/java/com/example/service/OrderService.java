package com.example.service;

import com.example.dto.request.OrderCreateRequestDTO;
import com.example.entity.BookEntity;
import com.example.entity.OrderEntity;
import com.example.repository.BookRepository;
import com.example.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    @Transactional
    public OrderEntity createOrder(OrderCreateRequestDTO request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();


        if (request.getBookIds() != null && !request.getBookIds().isEmpty()) {
            for (Long bookId : request.getBookIds()) {
                BookEntity book = bookRepository.findById(bookId)
                        .orElseThrow(() -> new RuntimeException("Kitab tapılmadı: ID " + bookId));


                if (book.getQuantity() == null || book.getQuantity() <= 0) {
                    throw new RuntimeException("Stokda kifayət qədər kitab yoxdur: " + book.getName());
                }


                book.setQuantity(book.getQuantity() - 1);


                bookRepository.save(book);
            }
        }


        OrderEntity order = OrderEntity.builder()
                .username(username)
                .address(request.getAddress())
                .cardNumber(request.getCardNumber())
                .bookIds(request.getBookIds())
                .orderDate(LocalDateTime.now())
                .build();

        return orderRepository.save(order);
    }

    public List<OrderEntity> getMyOrders() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderRepository.findByUsername(username);
    }
}