package com.example.service;

import com.example.dto.request.CreateOrderRequestDTO;
import com.example.dto.response.OrderItemResponseDTO;
import com.example.dto.response.OrderResponseDTO;
import com.example.entity.*;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository; private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository; private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository; private final BookRepository bookRepository;
    @Transactional
    public OrderResponseDTO createOrder(String username, CreateOrderRequestDTO request) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        CartEntity cart = cartRepository.findByUserUsername(username).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        List<CartItemEntity> cartItems = cartItemRepository.findByCartId(cart.getId());
        if (cartItems.isEmpty()) throw new IllegalArgumentException("Cart is empty");
        for (CartItemEntity ci : cartItems) {
            BookEntity book = bookRepository.findById(ci.getBook().getId()).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
            if (book.getQuantity() < ci.getQuantity()) throw new IllegalArgumentException("Not enough books in stock: " + book.getName());
            if (book.getPrice() == null) throw new IllegalArgumentException("Book price is not set: " + book.getName());
        }
        OrderEntity order = new OrderEntity(); order.setUser(user); order.setAddress(request.getAddress().trim()); order.setTotalPrice(0.0); order = orderRepository.save(order);
        double total = 0;
        for (CartItemEntity ci : cartItems) {
            BookEntity book = bookRepository.findById(ci.getBook().getId()).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
            book.setQuantity(book.getQuantity() - ci.getQuantity()); bookRepository.save(book);
            OrderItemEntity oi = new OrderItemEntity(); oi.setOrder(order); oi.setBook(book); oi.setBookName(book.getName()); oi.setQuantity(ci.getQuantity()); oi.setPrice(book.getPrice()); orderItemRepository.save(oi);
            total += book.getPrice() * ci.getQuantity();
        }
        order.setTotalPrice(total); orderRepository.save(order); cartItemRepository.deleteAll(cartItems); return map(order);
    }
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getMyOrders(String username) {
        List<OrderResponseDTO> result = new ArrayList<>();
        for (OrderEntity order : orderRepository.findByUserUsernameOrderByCreatedAtDesc(username)) result.add(map(order));
        return result;
    }
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long id, String username) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (!order.getUser().getUsername().equals(username)) throw new AccessDeniedException("You can only view your own orders");
        return map(order);
    }
    private OrderResponseDTO map(OrderEntity order) {
        OrderResponseDTO dto = new OrderResponseDTO(); dto.setId(order.getId()); dto.setUsername(order.getUser().getUsername()); dto.setAddress(order.getAddress()); dto.setTotalPrice(order.getTotalPrice()); dto.setCreatedAt(order.getCreatedAt());
        List<OrderItemResponseDTO> items = new ArrayList<>();
        for (OrderItemEntity oi : orderItemRepository.findByOrderId(order.getId())) { OrderItemResponseDTO item = new OrderItemResponseDTO(); item.setBookId(oi.getBook().getId()); item.setBookName(oi.getBookName()); item.setQuantity(oi.getQuantity()); item.setPrice(oi.getPrice()); items.add(item); }
        dto.setItems(items); return dto;
    }
}
