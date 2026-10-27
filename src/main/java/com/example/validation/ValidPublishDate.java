package com.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PublishDateValidator.class)
@Documented
public @interface ValidPublishDate {
    String message() default "1500-cu ilden bu gune olmalidir";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}