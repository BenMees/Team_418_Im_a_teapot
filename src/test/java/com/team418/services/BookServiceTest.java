package com.team418.services;

import com.team418.domain.Author;
import com.team418.domain.Book;
import com.team418.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
    BookService bookService;

    @BeforeEach
    void init() {
        BookRepository bookRepository = new BookRepository();

        bookService = new BookService(bookRepository);
    }

    @Test
    void getBooksByIsbn_givenPartIsbn_thenGetBooksContainingIsbn() {
        String regex = "1245789.*";

        Author author = new Author("jk", "R");

        Book book1 = new Book("1245789999999", "HarryPotter", author, "sorcery");
        Book book2 = new Book("1245789625689", "HarryPotter 2", author, "sorcery");
        Book book3 = new Book("1245799625689", "fake HarryPotter", author, "sorcery");

        bookService.saveBook(book1);
        bookService.saveBook(book2);
        bookService.saveBook(book3);

        List<Book> booksReturned = bookService.getBooksByIsbn(regex);

        Assertions.assertThat(booksReturned).containsExactlyInAnyOrder(book1,book2);
    }
}