package com.team418.repository;

import com.team418.domain.Author;
import com.team418.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {

    BookRepository bookRepository;

    @Test
    void whenGettingAllBooks_WeGetTheTwoBooks() {
        bookRepository = new BookRepository();
        Book book1 = new Book("132165","Title",new Author("name","secondName"),"dsdf");
        Book book2 = new Book("654","Title",new Author("name","secondName"),"dsdf");

        Map<String, Book> books = new HashMap<>();
        books.put(book1.getUniqueId(),book1);
        books.put(book2.getUniqueId(),book2);

        bookRepository.addBook(book1);
        bookRepository.addBook(book2);

        Assertions.assertThat(bookRepository.getBooks()).isEqualTo(books);
    }

    @Test
    void whenRepository_HasNoBooks_weGetEmptyMapBack() {
        bookRepository = new BookRepository();
        Assertions.assertThat(bookRepository.getBooks().size()).isEqualTo(0);
    }
}