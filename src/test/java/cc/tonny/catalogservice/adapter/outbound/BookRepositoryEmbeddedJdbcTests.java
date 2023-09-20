package cc.tonny.catalogservice.adapter.outbound;

import cc.tonny.catalogservice.config.DataConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

@DataJdbcTest
@Import(DataConfig.class)
public class BookRepositoryEmbeddedJdbcTests extends BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Override
    BookRepository bookRepository() {
        return bookRepository;
    }

    @Override
    JdbcAggregateTemplate jdbcAggregateTemplate() {
        return jdbcAggregateTemplate;
    }
}
