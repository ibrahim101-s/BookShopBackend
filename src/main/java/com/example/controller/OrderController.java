package com.example.controller;
import com.example.dto.request.CreateOrderRequestDTO;
import com.example.dto.response.OrderResponseDTO;
import com.example.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/orders") @RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping @Operation(summary = "Create order", description = "Checks stock, creates an order and clears the cart")
    public OrderResponseDTO create(@AuthenticationPrincipal UserDetails user, @Valid @RequestBody CreateOrderRequestDTO request) { return orderService.createOrder(user.getUsername(), request); }
    @GetMapping("/my") @Operation(summary = "Get my orders", description = "Returns orders of the authenticated user")
    public List<OrderResponseDTO> myOrders(@AuthenticationPrincipal UserDetails user) { return orderService.getMyOrders(user.getUsername()); }
    @GetMapping("/{id}") @Operation(summary = "Get order by ID", description = "Only the owner can view the order")
    public OrderResponseDTO get(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) { return orderService.getOrderById(id, user.getUsername()); }
}
