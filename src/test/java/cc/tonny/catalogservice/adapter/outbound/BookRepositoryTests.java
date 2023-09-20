package cc.tonny.catalogservice.adapter.outbound;

import cc.tonny.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BookRepositoryTests {
    abstract BookRepository bookRepository();
    abstract JdbcAggregateTemplate jdbcAggregateTemplate();


    @Test
    void findBookByIsbnWhenExisting() {
        var bookIsbn = "1234561237";
        var book = Book.of(bookIsbn, "Title", "Author", "12.90", "pigeon");
        jdbcAggregateTemplate().insert(book);

        var actualBook = bookRepository().findByIsbn(bookIsbn);

        assertThat(actualBook).isPresent();
        assertThat(actualBook.get().isbn()).isEqualTo(book.isbn());
    }
}
