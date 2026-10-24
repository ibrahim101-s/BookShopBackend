package com.example.controller;
import com.example.dto.request.BookAddRequestDTO;
import com.example.dto.request.BookFilterRequestDTO;
import com.example.dto.request.BookUpdateRequestDTO;
import com.example.dto.response.BookResponseDTO;
import com.example.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/books") @RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    @PostMapping @Operation(summary = "Add book", description = "Adds a book for any authenticated user")
    public BookResponseDTO addBook(@Valid @RequestBody BookAddRequestDTO request, @AuthenticationPrincipal UserDetails user) { return bookService.addBook(request, user.getUsername()); }
    @GetMapping @Operation(summary = "List books", description = "Returns a paginated list of books")
    public Page<BookResponseDTO> getBooks(@PageableDefault(size = 10, sort = "name") Pageable pageable) { return bookService.getBooks(pageable); }
    @GetMapping("/filter") @Operation(summary = "Search books", description = "Filters books by name and author with pagination")
    public Page<BookResponseDTO> filter(@ModelAttribute BookFilterRequestDTO filter, @PageableDefault(size = 10, sort = "name") Pageable pageable) { return bookService.filterBooks(filter, pageable); }
    @GetMapping("/{id}") @Operation(summary = "Get book", description = "Returns a book by ID")
    public BookResponseDTO getBook(@PathVariable Long id) { return bookService.getBook(id); }
    @PutMapping("/{id}") @Operation(summary = "Update book", description = "Updates a book")
    public BookResponseDTO update(@PathVariable Long id, @Valid @RequestBody BookUpdateRequestDTO request, @AuthenticationPrincipal UserDetails user) { return bookService.updateBook(id, request, user.getUsername()); }
    @DeleteMapping("/{id}") @Operation(summary = "Delete book", description = "Deletes a book")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) { bookService.deleteBook(id, user.getUsername()); }
}
