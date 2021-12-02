package com.team418.api.book;

import com.team418.Utility;
import com.team418.api.book.dto.BookDto;
import com.team418.api.book.dto.CreateBookDto;
import com.team418.domain.Author;
import com.team418.domain.Book;
import com.team418.domain.user.Librarian;
import com.team418.repository.BookRepository;
import com.team418.repository.LibrarianRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookControllerTest {

    @Value("${server.port}")
    private int port;
    private LibrarianRepository librarianRepository;
    private BookRepository bookRepository;

    @Autowired
    BookControllerTest(LibrarianRepository librarianRepository, BookRepository bookRepository) {
        this.librarianRepository = librarianRepository;
        this.bookRepository = bookRepository;
    }

    @BeforeAll
    public void setUp() {
        Librarian tom = new Librarian("tom", "tom", "tom@tom.tom");
        librarianRepository.addLibrarian(tom);
    }

    @Test
    void createBook_givenABookToCreate_thenTheNewlyCreatedBookIsSavedAndReturned() {
        CreateBookDto createBookDto = new CreateBookDto()
                .setIsbn("1234567")
                .setTitle("How To Integration Test")
                .setAuthor(new Author("tom", "tom"))
                .setSummary("short summary");

        BookDto bookDto =
                RestAssured
                        .given()
                        .body(createBookDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization("tom@tom.tom", "1234"))
                        .when()
                        .port(port)
                        .post("/books")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(BookDto.class);

        assertThat(bookDto.getIsbn()).isEqualTo(createBookDto.getIsbn());
        assertThat(bookDto.getTitle()).isEqualTo(createBookDto.getTitle());
        assertThat(bookDto.getAuthor()).isEqualTo(createBookDto.getAuthor());
        assertThat(bookDto.getSummary()).isEqualTo(createBookDto.getSummary());
    }

    @Test
    void givenABook_whenDeletingThatBook_thenBookIsSoftDeleted() {
        Book book = new Book("1234567", "Integration test", new Author("tom", "tom"), "summary");
        bookRepository.saveBook(book);

        RestAssured
                .given()
                .header("Authorization", "Basic dG9tQHRvbS50b206")
                .when()
                .port(port)
                .delete("/books/" + book.getUniqueId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        assertThat(bookRepository.getBook(book.getUniqueId()).isDeleted()).isTrue();
    }
}