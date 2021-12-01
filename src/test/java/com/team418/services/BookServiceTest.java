package com.team418.services;

import com.team418.domain.Author;
import com.team418.domain.Book;
import com.team418.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


class BookServiceTest {
    BookService bookService;

    @BeforeEach
    void init() {
        BookRepository bookRepository = new BookRepository();

        bookService = new BookService(bookRepository);
    }

    @Test
    void searchBooksCorrespondingIsbnPattern_givenPartIsbn_thenGetBooksContainingIsbn() {
        String regex = "1245789*";

        Author author = new Author("jk", "R");

        Book book1 = new Book("1245789999999", "HarryPotter", author, "sorcery");
        Book book2 = new Book("1245789625689", "HarryPotter 2", author, "sorcery");
        Book book3 = new Book("1245799625689", "fake HarryPotter", author, "sorcery");

        bookService.saveBook(book1);
        bookService.saveBook(book2);
        bookService.saveBook(book3);

        List<Book> booksReturned = bookService.searchBooksCorrespondingIsbnPattern(regex);

        Assertions.assertThat(booksReturned).containsExactlyInAnyOrder(book1,book2);
    }

    @Test
    void searchBooksCorrespondingIsbnPattern_givenFullIsbn_thenGetOneBook() {
        String regex = "1245789999999";

        Author author = new Author("jk", "R");

        Book book1 = new Book("1245789999999", "HarryPotter", author, "sorcery");
        Book book2 = new Book("1245789625689", "HarryPotter 2", author, "sorcery");
        Book book3 = new Book("1245799625689", "fake HarryPotter", author, "sorcery");

        bookService.saveBook(book1);
        bookService.saveBook(book2);
        bookService.saveBook(book3);

        List<Book> booksReturned = bookService.searchBooksCorrespondingIsbnPattern(regex);

        Assertions.assertThat(booksReturned).containsExactlyInAnyOrder(book1);
    }

    @Test
    void searchBooksCorrespondingIsbnPattern_givenIsbnThatDoesntExist_thenEmptyList() {
        String regex = "duck*";

        Author author = new Author("jk", "R");

        Book book1 = new Book("1245789999999", "HarryPotter", author, "sorcery");
        Book book2 = new Book("1245789625689", "HarryPotter 2", author, "sorcery");
        Book book3 = new Book("1245799625689", "fake HarryPotter", author, "sorcery");

        bookService.saveBook(book1);
        bookService.saveBook(book2);
        bookService.saveBook(book3);

        List<Book> booksReturned = bookService.searchBooksCorrespondingIsbnPattern(regex);

        Assertions.assertThat(booksReturned).isEmpty();
    }

    @Test
    void searchBooksCorrespondingIsbnPattern_givenOnlyWildcard_thenReturnsAllBooks() {
        String regex = "*";

        Author author = new Author("jk", "R");

        Book book1 = new Book("1245789999999", "HarryPotter", author, "sorcery");
        Book book2 = new Book("1245789625689", "HarryPotter 2", author, "sorcery");
        Book book3 = new Book("1245799625689", "fake HarryPotter", author, "sorcery");

        bookService.saveBook(book1);
        bookService.saveBook(book2);
        bookService.saveBook(book3);

        List<Book> booksReturned = bookService.searchBooksCorrespondingIsbnPattern(regex);

        Assertions.assertThat(booksReturned).containsExactlyInAnyOrder(book1,book2,book3);
    }

    @Test
    void getBookByIsbn_givenFullIsbn_thenReturnsOneBookCorresponding(){
        String isbn = "1245789625689";
        Author author = new Author("jk", "R");

        Book book1 = new Book("1245789999999", "HarryPotter", author, "sorcery");
        Book book2 = new Book(isbn, "HarryPotter 2", author, "sorcery");
        Book book3 = new Book("1245799625689", "fake HarryPotter", author, "sorcery");

        bookService.saveBook(book1);
        bookService.saveBook(book2);
        bookService.saveBook(book3);

        Book booksReturned = bookService.getBookByIsbn(isbn);

        Assertions.assertThat(booksReturned).isEqualTo(book2);
    }
}