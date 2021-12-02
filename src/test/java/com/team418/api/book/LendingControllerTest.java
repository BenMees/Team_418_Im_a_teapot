package com.team418.api.book;


import com.team418.Utility;
import com.team418.api.lending.dto.CreateLendingDto;
import com.team418.api.lending.dto.LendingDto;
import com.team418.domain.Address;
import com.team418.domain.Author;
import com.team418.domain.Book;
import com.team418.domain.lending.Lending;
import com.team418.domain.user.Admin;
import com.team418.domain.user.Librarian;
import com.team418.domain.user.Member;
import com.team418.repository.AdminRepository;
import com.team418.repository.BookRepository;
import com.team418.repository.LibrarianRepository;
import com.team418.repository.MemberRepository;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LendingControllerTest {

    public static final String ISBN = "134567";
    @Value("${server.port}")
    private int port;

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private Member member;

    @Autowired
    public LendingControllerTest(MemberRepository memberRepository, BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    @BeforeAll
    public void setup() {
        Address address = new Address("Sesame Street", "221B", "9900", "Leuven");
        Author author = new Author("J", "K");
        Book book = new Book(ISBN, "Harry Potter", author, "Magic");
        member = new Member("Speedy", "Gonzales", "speedy.gonzales@outlook.com", "789456", address);
        bookRepository.saveBook(book);
        memberRepository.addMember(member);
    }

    @Test
    void createLending_givenALendingToCreate_thenTheNewlyCreatedLendingIsSavedAndReturned() {
        CreateLendingDto createLendingDto = new CreateLendingDto(ISBN);

        LendingDto lendingDto =
                RestAssured
                        .given()
                        .body(createLendingDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
                        .when()
                        .port(port)
                        .post("/lendings")
                        .then()
                        .assertThat()
                        .statusCode(HttpServletResponse.SC_CREATED)
                        .extract()
                        .as(LendingDto.class);

        assertThat(lendingDto.bookIsbn()).isEqualTo(createLendingDto.isbn());
        assertThat(lendingDto.memberId()).isEqualTo(member.getUniqueId());
        assertThat(lendingDto.dueDate()).isEqualTo(LocalDate.now().plusWeeks(3)); // check if due
    }

    @Test
    void whenAdminTriesToLendABook_itGeneratesAnException() {
        Admin admin = new Admin("Zinedine", "Zidane", "zinedine.zidane@uefa.com");
        AdminRepository adminRepository = new AdminRepository();
        adminRepository.addNewAdmin(admin);
        CreateLendingDto createLendingDto = new CreateLendingDto(ISBN);

        RestAssured
                .given()
                .body(createLendingDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("zinedine.zidane@uefa.com", "234"))
                .when()
                .port(port)
                .post("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void whenLibrarianTriesToLendABook_itGeneratesAnException() {
        Librarian librarian = new Librarian("Zinedine", "Zidane", "zin.zidane@uefa.com");
        LibrarianRepository librarianRepository = new LibrarianRepository();
        librarianRepository.addLibrarian(librarian);
        CreateLendingDto createLendingDto = new CreateLendingDto(ISBN);

        RestAssured
                .given()
                .body(createLendingDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("zin.zidane@uefa.com", "234"))
                .when()
                .port(port)
                .post("/lendings")
                .then()
                .assertThat()
                .statusCode(401);
    }

    @Test
    void whenBookNotExistingToRent_giveBackCorrectException() {
        CreateLendingDto createLendingDto = new CreateLendingDto("0000");  // Should not excist

        RestAssured
                .given()
                .body(createLendingDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
                .when()
                .port(port)
                .post("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpServletResponse.SC_BAD_REQUEST);
    }


    @Test
    void whenBookSoftDeleted_giveBackCorrectException() {

        Author author = new Author("M", "S");
        Book bookSoftDeleted = new Book("654", "Harry Potter", author, "2");
        bookRepository.saveBook(bookSoftDeleted);
        bookSoftDeleted.setDeleted(true);

        CreateLendingDto createLendingDto = new CreateLendingDto("654");

        RestAssured
                .given()
                .body(createLendingDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
                .when()
                .port(port)
                .post("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpServletResponse.SC_EXPECTATION_FAILED);
    }


    @Test
    void whenBookNotAvailableToRent_giveBackCorrectException() {
        CreateLendingDto createLendingDto = new CreateLendingDto(ISBN);

        RestAssured
                .given()
                .body(createLendingDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
                .when()
                .port(port)
                .post("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpServletResponse.SC_EXPECTATION_FAILED);
    }


    //  possible checks:
    // return a book we have lent before (and are on time)
    // return a book when we are to late
    // return other Lending no from us
    // return Lending not existing
    // return Librarian and Admin should give error


    @Test
    void whenGivenCorrectLendingId_returnTheBookCorrectly() {
        Author author = new Author("PS", "TK");
        Book book = new Book("521", "Lent Out Book", author, "Coding Magic");

        book.lent();
        bookRepository.saveBook(book);

        Lending lending = new Lending(book.getIsbn(),member.getUniqueId());

        String expectedResult =  "Book is returned on time";

        String actualResult = RestAssured
                .given()
                .body(lending.getUniqueId())
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
                .when()
                .port(port)
                .delete("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(String.class);

        assertThat(actualResult).isEqualTo(expectedResult);
        assertThat(lending.isReturned()).isTrue();
    }

    @Test
    void returningABookWith_aLendingIdThatDoesntMatchAnyExistingLendingId() {

        String lendingId = "helloIShouldBe_ALendingID_ButImJust_aRandomSentenceToFailThisTest";

        RestAssured
                .given()
                .body(lendingId)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
                .when()
                .port(port)
                .delete("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
