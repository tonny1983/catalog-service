package cc.tonny.catalogservice.contract;


import cc.tonny.catalogservice.adapter.inbound.BookController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public abstract class BaseTestsClass {
    @Autowired
    BookController bookController;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder =
                MockMvcBuilders.standaloneSetup(bookController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}
