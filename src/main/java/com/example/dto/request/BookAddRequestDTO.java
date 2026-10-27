package com.example.dto.request;

import com.example.validation.UniqueBarcode;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookAddRequestDTO {

    @NotBlank(message = "Kitab adı boş ola bilməz")
    private String name;

    @NotBlank(message = "Müəllif adı boş ola bilməz")
    private String author;

    @NotNull(message = "Say qeyd edilməlidir")
    @Min(value = 1, message = "Minimum say 1 olmalıdır")
    private Integer quantity;

    @NotNull(message = "Səhifə sayı qeyd edilməlidir")
    @Min(value = 1, message = "Səhifə sayı müsbət olmalıdır")
    private Integer pageCount;

    @NotNull(message = "Nəşr tarixi qeyd edilməlidir")
    @PastOrPresent(message = "Nəşr tarixi gələcəkdə ola bilməz")
    private LocalDate publishDate;

    @NotNull(message = "Qiymət qeyd edilməlidir")
    @Positive(message = "Qiymət müsbət olmalıdır")
    private Double price;


    private String barcode;

    private String imageUrl;
}