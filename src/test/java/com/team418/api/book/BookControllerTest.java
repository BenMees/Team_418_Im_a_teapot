package com.team418.api.book;

import com.team418.Utility;
import com.team418.api.book.dto.BookDto;
import com.team418.api.book.dto.CreateBookDto;
import com.team418.api.book.dto.UpdateBookDto;
import com.team418.domain.Address;
import com.team418.domain.Author;
import com.team418.domain.Book;
import com.team418.domain.user.Librarian;
import com.team418.domain.user.Member;
import com.team418.repository.BookRepository;
import com.team418.repository.LibrarianRepository;
import com.team418.repository.MemberRepository;
import com.team418.services.BookService;
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
    private Book book;
    private final LibrarianRepository librarianRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final MemberRepository memberRepository;

    @Autowired
    BookControllerTest(LibrarianRepository librarianRepository, BookRepository bookRepository, BookService bookService, MemberRepository memberRepository) {
        this.librarianRepository = librarianRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.memberRepository = memberRepository;
    }

    @BeforeAll
    public void setUp() {
        Librarian tom = new Librarian("tom", "tom", "tom@tom.tom");
        librarianRepository.addLibrarian(tom);
        Address address = new Address("Sesame Street", "221B", "9900", "Leuven");
        Member member = new Member("Justin", "Member", "justin@member.com", "1234567", address);
        memberRepository.addMember(member);
        book = new Book("1234567", "How To Integration Test", new Author("Tom", "Tom"), "Short summary");
        bookRepository.saveBook(book);
    }

    @Test
    void createBook_givenABookToCreate_thenTheNewlyCreatedBookIsSavedAndReturned() {
        CreateBookDto createBookDto = new CreateBookDto("12345678", "How To Integration Test", new Author("tom", "tom"), "short summary");

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

        System.out.println(bookDto);

        assertThat(bookDto.isbn()).isEqualTo(createBookDto.isbn());
        assertThat(bookDto.title()).isEqualTo(createBookDto.title());
        assertThat(bookDto.author()).isEqualTo(createBookDto.author());
        assertThat(bookDto.summary()).isEqualTo(createBookDto.summary());
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

    @Test
    void updateBook_givenABookToUpdate_thenTheBookIsUpdatedAndReturned() {
        UpdateBookDto updateBookDto = new UpdateBookDto()
                .setTitle("Integration tests vol. 2")
                .setSummary("Newer and better");

        BookDto bookDto =
                RestAssured
                        .given()
                        .body(updateBookDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization("tom@tom.tom", "1234"))
                        .when()
                        .port(port)
                        .put("/books/" + book.getUniqueId())
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(BookDto.class);

        assertThat(bookDto.title()).isEqualTo(updateBookDto.getTitle());
        assertThat(bookDto.author()).isEqualTo(updateBookDto.getAuthor());
        assertThat(bookDto.summary()).isEqualTo(updateBookDto.getSummary());
        assertThat(bookService.getBook(book.getUniqueId()).getTitle()).isEqualTo(updateBookDto.getTitle());

    }

    @Test
    void restoreBook_givenABookToRestore_thenTheBookIsRestoredAndReturned() {
        book.setDeleted(true);
        UpdateBookDto updateBookDto = new UpdateBookDto()
                .setDeleted(false);

        BookDto bookDto =
                RestAssured
                        .given()
                        .body(updateBookDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization("tom@tom.tom", "1234"))
                        .when()
                        .port(port)
                        .put("/books/restore/" + book.getUniqueId())
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(BookDto.class);

        assertThat(bookDto.title()).isEqualTo(book.getTitle());
        assertThat(bookDto.author()).isEqualTo(book.getAuthor());
        assertThat(bookDto.summary()).isEqualTo(book.getSummary());
        assertThat(bookService.getBook(book.getUniqueId()).isDeleted()).isEqualTo(false);
    }

    @Test
    void getBook_givenADeletedBookAndMemberAccess_thenAnErrorIsReturned() {
        book.setDeleted(true);

        RestAssured
                .given()
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("justin@member.com", "1234"))
                .when()
                .port(port)
                .get("/books/" + book.getUniqueId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void getBook_givenADeletedBookAndLibrarianAccess_thenTheBookIsReturned() {
        book.setDeleted(true);

        BookDto bookDto =
                RestAssured
                        .given()
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization("tom@tom.tom", "1234"))
                        .when()
                        .port(port)
                        .get("/books/" + book.getUniqueId())
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(BookDto.class);

        assertThat(bookDto.title()).isEqualTo(book.getTitle());
        assertThat(bookDto.author()).isEqualTo(book.getAuthor());
        assertThat(bookDto.summary()).isEqualTo(book.getSummary());
        assertThat(bookService.getBook(book.getUniqueId()).isDeleted()).isEqualTo(true);
    }

    @Test
    void getBook_givenADeletedBookAndAnUnknownUser_thenAnErrorIsReturned() {
        book.setDeleted(true);

        RestAssured
                .given()
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("no@such.user", "1234"))
                .when()
                .port(port)
                .get("/books/" + book.getUniqueId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void getBooks_givenTwoBooks_thenTwoBooksAreReturned() {
        Book book1 = new Book("789", "second book", new Author("Tim", "Mi"), "A whole new book");
        book.setDeleted(false);
        bookRepository.saveBook(book1);

        BookDto bookdto = BookMapper.bookToDto(book);
        BookDto book1dto = BookMapper.bookToDto(book1);

        BookDto[] bookDtos =
                RestAssured
                        .given()
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .get("/books")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(BookDto[].class);


        assertThat(bookDtos).hasSize(bookRepository.getBooks().size());
        assertThat(bookDtos).contains(bookdto, book1dto);
    }
}