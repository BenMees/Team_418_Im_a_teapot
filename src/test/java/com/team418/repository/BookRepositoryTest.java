package com.team418.repository;

import com.team418.domain.Author;
import com.team418.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {
    private Author author;
    private Book book;
    private BookRepository repository;

    @BeforeEach
    void setUp() {
        author = new Author("tom", "tom");
        book = new Book("123", "test", author, "short summary");
        repository = new BookRepository();
    }

    @Test
    void givenABookRepository_whenAddingABook_thenBookRepositoryContainsThatBook() {
        repository.saveBook(book);

        Assertions.assertThat(repository.getBooks().containsValue(book)).isTrue();
    }

    @Test
    void givenABookRepositoryWithOneBook_whenGettingOneBook_thenBookShouldBeReturned() {
        repository.saveBook(book);

        Book expectedBook = repository.getBook(book.getUniqueId());

        Assertions.assertThat(expectedBook).isEqualTo(book);
    }

    @Test
    void whenGettingAllBooks_WeGetTheTwoBooks() {
        repository = new BookRepository();
        Book book1 = new Book("132165","Title",new Author("name","secondName"),"dsdf");
        Book book2 = new Book("654","Title",new Author("name","secondName"),"dsdf");

        Map<String, Book> books = new HashMap<>();
        books.put(book1.getUniqueId(),book1);
        books.put(book2.getUniqueId(),book2);

        repository.saveBook(book1);
        repository.saveBook(book2);

        Assertions.assertThat(repository.getBooks()).isEqualTo(books);
    }

    @Test
    void whenRepository_HasNoBooks_weGetEmptyMapBack() {
        repository = new BookRepository();
        Assertions.assertThat(repository.getBooks().size()).isEqualTo(0);
    }
}