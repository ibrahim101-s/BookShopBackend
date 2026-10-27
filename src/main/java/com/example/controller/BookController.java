package com.example.controller;

import com.example.dto.request.BookAddRequestDTO;
import com.example.dto.response.BookResponseDTO;
import com.example.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Book Controller", description = "Kitabların CRUD, Axtarış və Filter əməliyyatları")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Kitab əlavə et", description = "Yeni kitab yaradır")
    public ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookAddRequestDTO request) {
        return ResponseEntity.ok(bookService.addBook(request));
    }

    @GetMapping
    @Operation(summary = "Bütün kitabları gətir", description = "Pagination ilə kitabların siyahısını gətirir")
    public ResponseEntity<Page<BookResponseDTO>> getAllBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Kitabı ID ilə tap", description = "Tək kitab məlumatını qaytarır")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Ada görə axtarış", description = "Kitab adına görə Pageable axtarış")
    public ResponseEntity<Page<BookResponseDTO>> searchByName(@RequestParam String name, Pageable pageable) {
        return ResponseEntity.ok(bookService.searchByName(name, pageable));
    }

    @GetMapping("/filter")
    @Operation(summary = "Kitabları filterlə", description = "Ad, müəllif və qiymət aralığı üzrə filterləmə")
    public ResponseEntity<Page<BookResponseDTO>> filterBooks(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Pageable pageable) {
        return ResponseEntity.ok(bookService.filterBooks(name, author, minPrice, maxPrice, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Kitabı yenilə", description = "Mövcud kitabı redaktə edir")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookAddRequestDTO request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Kitabı sil", description = "ID-yə görə kitabı silir")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}