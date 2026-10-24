package com.example.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import com.example.validation.ValidPublishDate;

@Getter @Setter
public class BookUpdateRequestDTO {
    @NotBlank(message = "Book name is required") private String name;
    @NotBlank(message = "Author is required") private String author;
    @NotNull(message = "Quantity is required") @Min(value = 0, message = "Quantity cannot be negative") private Integer quantity;
    @NotNull(message = "Page count is required") @Min(value = 1, message = "Page count must be at least 1") private Integer pageCount;
    @NotNull(message = "Publish date is required") @ValidPublishDate private LocalDate publishDate;
    @NotNull(message = "Price is required") @DecimalMin(value = "0.0", message = "Price cannot be negative") private Double price;
    @NotBlank(message = "Barcode is required") @Size(max = 100, message = "Barcode is too long") private String barcode;
    private String imageUrl;
}
