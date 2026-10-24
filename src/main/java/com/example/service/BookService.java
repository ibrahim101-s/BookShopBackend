package com.example.service;

import com.example.dto.request.BookAddRequestDTO;
import com.example.dto.request.BookFilterRequestDTO;
import com.example.dto.request.BookUpdateRequestDTO;
import com.example.dto.response.BookResponseDTO;
import com.example.entity.BookEntity;
import com.example.exception.BarcodeAlreadyExistsException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.BookRepository;
import com.example.specification.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public BookResponseDTO addBook(BookAddRequestDTO request, String username) {
        if (bookRepository.existsByBarcode(request.getBarcode())) throw new BarcodeAlreadyExistsException("Barcode already exists");
        BookEntity book = new BookEntity();
        copy(request, book); book.setUsername(username);
        return map(bookRepository.save(book));
    }
    public Page<BookResponseDTO> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::map);
    }
    public Page<BookResponseDTO> filterBooks(BookFilterRequestDTO filter, Pageable pageable) {
        return bookRepository.findAll(BookSpecification.filter(filter.getName(), filter.getAuthor()), pageable).map(this::map);
    }
    public BookResponseDTO getBook(Long id) { return map(findBook(id)); }
    @Transactional
    public BookResponseDTO updateBook(Long id, BookUpdateRequestDTO request, String username) {
        BookEntity book = findBook(id);
        if (bookRepository.existsByBarcodeAndIdNot(request.getBarcode(), id)) throw new BarcodeAlreadyExistsException("Barcode already exists");
        copy(request, book);
        return map(bookRepository.save(book));
    }
    @Transactional
    public void deleteBook(Long id, String username) {
        BookEntity book = findBook(id); bookRepository.delete(book);
    }
    private BookEntity findBook(Long id) { return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id)); }
    private void copy(BookAddRequestDTO source, BookEntity target) {
        target.setName(source.getName().trim()); target.setAuthor(source.getAuthor().trim()); target.setQuantity(source.getQuantity());
        target.setPageCount(source.getPageCount()); target.setPublishDate(source.getPublishDate()); target.setPrice(source.getPrice());
        target.setBarcode(source.getBarcode().trim()); target.setImageUrl(source.getImageUrl());
    }
    private void copy(BookUpdateRequestDTO source, BookEntity target) {
        target.setName(source.getName().trim()); target.setAuthor(source.getAuthor().trim()); target.setQuantity(source.getQuantity());
        target.setPageCount(source.getPageCount()); target.setPublishDate(source.getPublishDate()); target.setPrice(source.getPrice());
        target.setBarcode(source.getBarcode().trim()); target.setImageUrl(source.getImageUrl());
    }
    private BookResponseDTO map(BookEntity book) {
        BookResponseDTO response = new BookResponseDTO(); response.setId(book.getId()); response.setName(book.getName());
        response.setAuthor(book.getAuthor()); response.setQuantity(book.getQuantity()); response.setPageCount(book.getPageCount());
        response.setPublishDate(book.getPublishDate()); response.setUsername(book.getUsername()); response.setPrice(book.getPrice());
        response.setBarcode(book.getBarcode()); response.setImageUrl(book.getImageUrl()); return response;
    }
}
