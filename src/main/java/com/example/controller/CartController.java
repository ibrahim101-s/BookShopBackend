package com.example.controller;
import com.example.dto.request.AddCartItemRequestDTO;
import com.example.dto.request.UpdateCartItemRequestDTO;
import com.example.dto.response.CartResponseDTO;
import com.example.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/cart") @RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @GetMapping @Operation(summary = "Get cart", description = "Returns the current user cart")
    public CartResponseDTO getCart(@AuthenticationPrincipal UserDetails user) { return cartService.getCart(user.getUsername()); }
    @PostMapping("/items") @Operation(summary = "Add cart item", description = "Adds a book to the cart")
    public CartResponseDTO add(@AuthenticationPrincipal UserDetails user, @Valid @RequestBody AddCartItemRequestDTO request) { return cartService.addItem(user.getUsername(), request); }
    @PutMapping("/items/{itemId}") @Operation(summary = "Update cart item", description = "Updates the quantity of a cart item")
    public CartResponseDTO update(@AuthenticationPrincipal UserDetails user, @PathVariable Long itemId, @Valid @RequestBody UpdateCartItemRequestDTO request) { return cartService.updateItem(user.getUsername(), itemId, request); }
    @DeleteMapping("/items/{itemId}") @Operation(summary = "Remove cart item", description = "Removes an item from the cart")
    public CartResponseDTO remove(@AuthenticationPrincipal UserDetails user, @PathVariable Long itemId) { return cartService.removeItem(user.getUsername(), itemId); }
}
