package com.team418.repository;

import com.team418.domain.Author;
import com.team418.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}