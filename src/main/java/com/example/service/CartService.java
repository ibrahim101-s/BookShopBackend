package com.example.service;

import com.example.dto.request.AddCartItemRequestDTO;
import com.example.dto.request.UpdateCartItemRequestDTO;
import com.example.dto.response.CartItemResponseDTO;
import com.example.dto.response.CartResponseDTO;
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
public class CartService {
    private final CartRepository cartRepository; private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository; private final UserRepository userRepository;
    private CartEntity getOrCreateCart(String username) {
        return cartRepository.findByUserUsername(username).orElseGet(() -> {
            UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            CartEntity cart = new CartEntity(); cart.setUser(user); return cartRepository.save(cart);
        });
    }
    @Transactional(readOnly = true)
    public CartResponseDTO getCart(String username) { return mapToResponse(getOrCreateCart(username)); }
    @Transactional
    public CartResponseDTO addItem(String username, AddCartItemRequestDTO request) {
        CartEntity cart = getOrCreateCart(username);
        BookEntity book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        CartItemEntity item = cartItemRepository.findByCartIdAndBookId(cart.getId(), book.getId()).orElse(null);
        int quantity = request.getQuantity() + (item == null ? 0 : item.getQuantity());
        if (quantity > book.getQuantity()) throw new IllegalArgumentException("Not enough books in stock");
        if (item == null) { item = new CartItemEntity(); item.setCart(cart); item.setBook(book); }
        item.setQuantity(quantity); cartItemRepository.save(item); return mapToResponse(cart);
    }
    @Transactional
    public CartResponseDTO updateItem(String username, Long itemId, UpdateCartItemRequestDTO request) {
        CartItemEntity item = cartItemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        checkOwner(item, username);
        if (request.getQuantity() > item.getBook().getQuantity()) throw new IllegalArgumentException("Not enough books in stock");
        item.setQuantity(request.getQuantity()); cartItemRepository.save(item); return mapToResponse(item.getCart());
    }
    @Transactional
    public CartResponseDTO removeItem(String username, Long itemId) {
        CartItemEntity item = cartItemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        checkOwner(item, username); CartEntity cart = item.getCart(); cartItemRepository.delete(item); return mapToResponse(cart);
    }
    private void checkOwner(CartItemEntity item, String username) {
        if (!item.getCart().getUser().getUsername().equals(username)) throw new AccessDeniedException("You can only manage your own cart");
    }
    private CartResponseDTO mapToResponse(CartEntity cart) {
        CartResponseDTO response = new CartResponseDTO(); response.setId(cart.getId()); List<CartItemResponseDTO> items = new ArrayList<>(); double total = 0;
        for (CartItemEntity item : cartItemRepository.findByCartId(cart.getId())) {
            CartItemResponseDTO dto = new CartItemResponseDTO(); dto.setId(item.getId()); dto.setBookId(item.getBook().getId()); dto.setBookName(item.getBook().getName()); dto.setPrice(item.getBook().getPrice()); dto.setQuantity(item.getQuantity()); items.add(dto);
            total += item.getBook().getPrice() * item.getQuantity();
        }
        response.setItems(items); response.setTotalPrice(total); return response;
    }
}
