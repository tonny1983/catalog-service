package cc.tonny.catalogservice.domain;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BookValidationTest {
    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        var factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAlFieldsCorrectThenValidationSucceeds() {
        var book = new Book(1L, "1234567890", "Title", "Author", BigDecimal.ONE, "pigeon", null, null, 0);
        var violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails() {
        var book = new Book(1L, "a234567890", "Title", "Author", BigDecimal.ONE, "pigeon", null, null, 0);
        var violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("The ISBN format must be valid!");
    }
}
