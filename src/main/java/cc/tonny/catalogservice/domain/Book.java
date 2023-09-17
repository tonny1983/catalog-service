package cc.tonny.catalogservice.domain;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record Book(
        @NotBlank(message = "The book ISBN must be defined!")
        @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid!")
        String isbn,

        @NotBlank(message = "The book title must be defined!")
        String title,

        @NotBlank(message = "The book author must be defined!")
        String author,

        @DecimalMin(value = "0.0", inclusive = false, message = "The book price must be greater than zero!")
        @Digits(integer=9, fraction=2)
        @NotNull(message = "The book price must be defined!")
        BigDecimal price
) {
}
