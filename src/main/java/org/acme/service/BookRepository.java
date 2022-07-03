package org.acme.service;

import lombok.extern.slf4j.Slf4j;
import org.acme.entity.Book;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
@Slf4j
public class BookRepository {
    @ConfigProperty(name = "book.genre")
    private String genre;
    public List<Book> getAllBooks() {
        log.debug("Getting all books");
        List<Book> books = Stream.of(
                Book.builder()
                        .author("Peter Michel")
                        .id(1)
                        .genre(genre)
                        .title("Top Gun Maverick")
                        .yearOfPublication(2022)
                        .build()).collect(Collectors.toList());
        return books;
    }

    public Optional<Book> getBook(@PathParam("id") int id) {
        return getAllBooks().stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }
}
