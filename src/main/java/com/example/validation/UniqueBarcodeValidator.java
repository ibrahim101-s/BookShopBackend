package com.example.validation;

import com.example.repository.BookRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueBarcodeValidator implements ConstraintValidator<UniqueBarcode, String> {
    private final BookRepository bookRepository;

    @Override
    public boolean isValid(String barcode, ConstraintValidatorContext context) {
        if (barcode == null || barcode.isBlank()) {
            return true;
        }
        return !bookRepository.existsByBarcode(barcode.trim());
    }
}
