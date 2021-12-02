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
    Book book1;
    Book book2;
    Book book3;

    @BeforeEach
    void init() {
        BookRepository bookRepository = new BookRepository();
        bookService = new BookService(bookRepository);

        Author author = new Author("jk", "Rowling");
        Author author1 = new Author("Jon Kelvin", "Gnilwor");

        book1 = new Book("1245789999999", "HarryPotter", author, "sorcery");
        book2 = new Book("1245789625689", "HarryPotter 2", author, "sorcery");
        book3 = new Book("1245799625689", "fake HarryPotter", author1, "sorcery");

        bookService.saveBook(book1);
        bookService.saveBook(book2);
        bookService.saveBook(book3);
    }

    @Test
    void searchBooksCorrespondingIsbnPattern_givenPartIsbn_thenGetBooksContainingIsbn() {
        String regex = "1245789*";

        List<Book> booksReturned = bookService.searchBooksCorrespondingIsbnPattern(regex);

        Assertions.assertThat(booksReturned).containsExactlyInAnyOrder(book1,book2);
    }

    @Test
    void searchBooksCorrespondingIsbnPattern_givenFullIsbn_thenGetOneBook() {
        String regex = "1245789999999";

        List<Book> booksReturned = bookService.searchBooksCorrespondingIsbnPattern(regex);

        Assertions.assertThat(booksReturned).containsExactlyInAnyOrder(book1);
    }

    @Test
    void searchBooksCorrespondingIsbnPattern_givenIsbnThatDoesntExist_thenEmptyList() {
        String regex = "duck*";

        List<Book> booksReturned = bookService.searchBooksCorrespondingIsbnPattern(regex);

        Assertions.assertThat(booksReturned).isEmpty();
    }

    @Test
    void searchBooksCorrespondingIsbnPattern_givenOnlyWildcard_thenReturnsAllBooks() {
        String regex = "*";

        List<Book> booksReturned = bookService.searchBooksCorrespondingIsbnPattern(regex);

        Assertions.assertThat(booksReturned).containsExactlyInAnyOrder(book1,book2,book3);
    }

    @Test
    void getBookByIsbn_givenFullIsbn_thenReturnsOneBookCorresponding(){
        String isbn = "1245789625689";

        Book booksReturned = bookService.getBookByIsbn(isbn);

        Assertions.assertThat(booksReturned).isEqualTo(book2);
    }

    @Test
    void searchBooksCorrespondingAuthorPattern_givenWildcardAndLastname_thenReturnBooks() {
        String searchName = "*ling";

        List<Book> booksReturned = bookService.searchBooksCorrespondingAuthorPattern(searchName);

        Assertions.assertThat(booksReturned).containsExactlyInAnyOrder(book1, book2);
    }

    @Test
    void searchBooksCorrespondingAuthorPattern_givenWildcardAndFirstname_thenReturnBooks() {
        String searchName = "*kel*";

        List<Book> booksReturned = bookService.searchBooksCorrespondingAuthorPattern(searchName);

        Assertions.assertThat(booksReturned).containsExactlyInAnyOrder(book3);
    }

    @Test
    void searchBooksCorrespondingAuthorPattern_givenPartialFirstnameAndLastname_thenReturnBooks() {
        String searchName = "*vin gnil*";

        List<Book> booksReturned = bookService.searchBooksCorrespondingAuthorPattern(searchName);

        Assertions.assertThat(booksReturned).containsExactlyInAnyOrder(book3);
    }

    @Test
    void searchBooksCorrespondingAuthorPattern_givenPartialLastnameAndFirstname_thenReturnBooks() {
        String searchName = "*wor jo*";

        List<Book> booksReturned = bookService.searchBooksCorrespondingAuthorPattern(searchName);

        Assertions.assertThat(booksReturned).containsExactlyInAnyOrder(book3);
    }

    @Test
    void searchBooksCorrespondingTitlePattern_givenWildcardAndPartialTitle_thenReturnBooks() {
        String searchTitle = "*harry*";

        List<Book> booksReturned= bookService.searchBooksCorrespondingTitlePattern(searchTitle);

        Assertions.assertThat(booksReturned).containsExactlyInAnyOrder(book1, book2, book3);
    }

    @Test
    void searchBooksCorrespondingTitlePattern_givenPartialBeginningTitle_thenReturnBooks() {
        String searchTitle = "harry*";

        List<Book> booksReturned= bookService.searchBooksCorrespondingTitlePattern(searchTitle);

        Assertions.assertThat(booksReturned).containsExactlyInAnyOrder(book1, book2);
    }

    @Test
    void searchBooksCorrespondingTitlePattern_givenPartialEndingTitle_thenReturnBooks() {
        String searchTitle = "*potter";

        List<Book> booksReturned= bookService.searchBooksCorrespondingTitlePattern(searchTitle);

        Assertions.assertThat(booksReturned).containsExactlyInAnyOrder(book1, book3);
    }

}