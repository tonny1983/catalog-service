package cc.tonny.catalogservice.contract;


import cc.tonny.catalogservice.domain.Book;
import cc.tonny.catalogservice.domain.BookNotFoundException;
import cc.tonny.catalogservice.domain.BookService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@ActiveProfiles("contract")
public abstract class BaseTestsClass {
    @Autowired
    WebApplicationContext context;

    @MockBean
    BookService bookService;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.webAppContextSetup(context);

        when(bookService.viewBookDetails("1234567890"))
                .thenReturn(new Book(1L, "1234567890", "title", "author", BigDecimal.ONE, "publisher", Instant.now(), Instant.now(), 1));

        when(bookService.viewBookDetails("0987654321"))
                .thenThrow(new BookNotFoundException("0987654321"));
    }
}
