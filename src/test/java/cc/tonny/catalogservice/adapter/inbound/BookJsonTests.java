package cc.tonny.catalogservice.adapter.inbound;

import cc.tonny.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {
    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws IOException {
        var book = new Book(1L, "1234567890", "Title", "Author", BigDecimal.ONE, "wiley", null, null, 2);
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.id").asString().isEqualTo(book.id().toString());
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").asString().isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").asString().isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").asString().isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").asString().isEqualTo(book.price().toString());
        assertThat(jsonContent).extractingJsonPathStringValue("@.publisher").asString().isEqualTo(book.publisher());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.version").asString().isEqualTo(book.version() + "");
    }

    @Test
    void testDeserialize() throws IOException {
        var content = """                                                 
                {
                  "id": 2,
                  "isbn": "1234567890",
                  "title": "Title",
                  "author": "Author",
                  "price": 1,
                  "publisher": "wiley",
                  "version": 3
                }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Book(2L, "1234567890", "Title", "Author", BigDecimal.ONE, "wiley", null, null, 3));
    }
}
