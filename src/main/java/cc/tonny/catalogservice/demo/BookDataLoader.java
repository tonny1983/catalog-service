package cc.tonny.catalogservice.demo;

import cc.tonny.catalogservice.adapter.outbound.BookRepository;
import cc.tonny.catalogservice.domain.Book;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("testdata")
public class BookDataLoader {
    private final BookRepository bookRepository;

    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        var book1 = Book.of("1234567891", "Northern Lights",
                "Lyra Silverstar", "9.90", "pigeon");
        var book2 = Book.of("1234567892", "Polar Journey",
                "Iorek Polarson", "12.90", "wiley");
        bookRepository.saveAll(List.of(book1, book2));
    }
}
