package com.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class PublishDateValidator implements ConstraintValidator<ValidPublishDate, LocalDate> {

    private static final LocalDate MIN_DATE = LocalDate.of(1500, 1, 1);

    @Override
    public boolean isValid(LocalDate publishDate, ConstraintValidatorContext context) {
        if (publishDate == null) {
            return true;
        }
        LocalDate now = LocalDate.now();
        return !publishDate.isBefore(MIN_DATE) && !publishDate.isAfter(now);
    }
}