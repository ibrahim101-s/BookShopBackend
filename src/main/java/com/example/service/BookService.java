package com.example.service;

import com.example.dto.request.BookAddRequestDTO;
import com.example.dto.response.BookResponseDTO;
import com.example.entity.BookEntity;
import com.example.exception.NotFoundException;
import com.example.repository.BookRepository;
import com.example.specification.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public BookResponseDTO addBook(BookAddRequestDTO request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        BookEntity entity = BookEntity.builder()
                .name(request.getName())
                .author(request.getAuthor())
                .quantity(request.getQuantity())
                .pageCount(request.getPageCount())
                .publishDate(request.getPublishDate())
                .price(request.getPrice())
                .barcode(request.getBarcode())
                .imageUrl(request.getImageUrl())
                .username(username)
                .build();

        return mapToDTO(bookRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public Page<BookResponseDTO> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    public BookResponseDTO getBookById(Long id) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Kitab tapılmadı id: " + id));
        return mapToDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<BookResponseDTO> searchByName(String name, Pageable pageable) {
        return bookRepository.findByNameContainingIgnoreCase(name, pageable).map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    public Page<BookResponseDTO> filterBooks(String name, String author, Double minPrice, Double maxPrice, Pageable pageable) {
        Specification<BookEntity> spec = Specification.where(BookSpecification.hasName(name))
                .and(BookSpecification.hasAuthor(author))
                .and(BookSpecification.priceBetween(minPrice, maxPrice));

        return bookRepository.findAll(spec, pageable).map(this::mapToDTO);
    }

    @Transactional
    public BookResponseDTO updateBook(Long id, BookAddRequestDTO request) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Kitab tapılmadı id: " + id));

        validateOwnership(entity, "redaktə etməyə");


        if (request.getBarcode() != null && !request.getBarcode().isBlank()) {
            String cleanBarcode = request.getBarcode().trim();

            boolean existsInOtherBook = bookRepository.existsByBarcodeAndIdNot(cleanBarcode, id);
            if (existsInOtherBook) {
                throw new RuntimeException("Bu barkod başqa bir kitab üçün artıq istifadə olunub!");
            }

            entity.setBarcode(cleanBarcode);
        }

        entity.setName(request.getName());
        entity.setAuthor(request.getAuthor());
        entity.setQuantity(request.getQuantity());
        entity.setPageCount(request.getPageCount());
        entity.setPublishDate(request.getPublishDate());
        entity.setPrice(request.getPrice());

        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            entity.setImageUrl(request.getImageUrl());
        }

        return mapToDTO(bookRepository.save(entity));
    }

    @Transactional
    public void deleteBook(Long id) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Kitab tapılmadı id: " + id));

        validateOwnership(entity, "silməyə");

        bookRepository.delete(entity);
    }


    private void validateOwnership(BookEntity entity, String actionName) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();


        if (entity.getUsername() == null || entity.getUsername().isBlank()) {
            entity.setUsername(currentUsername);
            return;
        }


        if (!entity.getUsername().trim().equalsIgnoreCase(currentUsername.trim())) {
            throw new RuntimeException("Siz yalnız öz əlavə etdiyiniz kitabı " + actionName + " hüququna maliksiniz!");
        }
    }

    private BookResponseDTO mapToDTO(BookEntity entity) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAuthor(entity.getAuthor());
        dto.setQuantity(entity.getQuantity());
        dto.setPageCount(entity.getPageCount());
        dto.setPublishDate(entity.getPublishDate());
        dto.setUsername(entity.getUsername());
        dto.setPrice(entity.getPrice());
        dto.setBarcode(entity.getBarcode());
        dto.setImageUrl(entity.getImageUrl());
        return dto;
    }
}