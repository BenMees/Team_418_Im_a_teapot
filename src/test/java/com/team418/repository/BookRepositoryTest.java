package com.team418.repository;

import com.team418.domain.Author;
import com.team418.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

class BookRepositoryTest {
    private Book book1;
    private Book book2;
    private BookRepository repository;
    private Map<String, Book> books;

    @BeforeEach
    void setUp() {
        Author author = new Author("tom", "tom");
        book1 = new Book("123", "test", author, "short summary");
        book2 = new Book("654", "Title", author, "This is summary for book 2");
        repository = new BookRepository();
        books = new HashMap<>();
    }

    @Test
    void whenRepository_HasNoBooks_weGetEmptyMapBack() {
        repository = new BookRepository();
        Assertions.assertThat(repository.getBooks().size()).isEqualTo(0);
    }

    @Test
    void givenABookRepository_whenAddingABook_thenBookRepositoryContainsThatBook() {
        repository.saveBook(book1);

        Assertions.assertThat(repository.getBooks().contains(book1)).isTrue();
    }

    @Test
    void givenABookRepositoryWithOneBook_whenGettingOneBook_thenBookShouldBeReturned() {
        repository.saveBook(book1);

        Book expectedBook = repository.getBook(book1.getUniqueId());

        Assertions.assertThat(expectedBook).isEqualTo(book1);
    }

    @Test
    void whenGettingAllBooks_WeGetTheTwoBooks() {
        books.put(book1.getUniqueId(), book1);
        books.put(book2.getUniqueId(), book2);

        repository.saveBook(book1);
        repository.saveBook(book2);

        Assertions.assertThat(repository.getBooks()).isEqualTo(books.values().stream().toList());
    }
}