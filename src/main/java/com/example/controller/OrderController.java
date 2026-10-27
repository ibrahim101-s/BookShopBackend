package com.example.controller;

import com.example.dto.request.OrderCreateRequestDTO;
import com.example.entity.OrderEntity;
import com.example.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "Sifarişlərin yaradılması və baxılması")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Sifariş yarat", description = "Səbətdəki kitabları təsdiqləyib sifariş yaradır")
    public ResponseEntity<OrderEntity> createOrder(@Valid @RequestBody OrderCreateRequestDTO request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/my-orders")
    @Operation(summary = "İstifadəçinin sifarişləri", description = "Daxil olmuş istifadəçinin öz sifarişlərini görməsi")
    public ResponseEntity<List<OrderEntity>> getMyOrders() {
        return ResponseEntity.ok(orderService.getMyOrders());
    }
}