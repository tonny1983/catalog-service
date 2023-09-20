package cc.tonny.catalogservice.adapter.outbound;

import cc.tonny.catalogservice.config.DataConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@DataJdbcTest
@Import({DataConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryDockerJdbcTests extends BookRepositoryTests {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.4"));

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

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
