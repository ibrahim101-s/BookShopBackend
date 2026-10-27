package com.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueBarcodeValidator.class)
@Documented
public @interface UniqueBarcode {
    String message() default "Bu barcode mövcuddur";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}