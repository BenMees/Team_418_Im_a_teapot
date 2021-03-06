package com.team418.api.lending;


import com.team418.Utility;
import com.team418.api.lending.dto.AnswerReturnDto;
import com.team418.api.lending.dto.CreateLendingDto;
import com.team418.api.lending.dto.LendingDto;
import com.team418.api.lending.dto.ReturnLendingDto;
import com.team418.domain.Address;
import com.team418.domain.Author;
import com.team418.domain.Book;
import com.team418.domain.lending.Lending;
import com.team418.domain.user.Admin;
import com.team418.domain.user.Librarian;
import com.team418.domain.user.Member;
import com.team418.repository.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LendingControllerTest {

    public static final String ISBN = "134567";
    @Value("${server.port}")
    private int port;

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LibrarianRepository librarianRepository;
    private final LendingRepository lendingRepository;
    private Member member;

    @Autowired
    public LendingControllerTest(MemberRepository memberRepository, BookRepository bookRepository, LibrarianRepository librarianRepository, LendingRepository lendingRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.librarianRepository = librarianRepository;
        this.lendingRepository = lendingRepository;
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
                        .statusCode(CREATED.value())
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
                .statusCode(UNAUTHORIZED.value());
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
                .statusCode(HttpStatus.UNAUTHORIZED.value());
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
                .statusCode(BAD_REQUEST.value());
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
                .statusCode(EXPECTATION_FAILED.value());
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
                .statusCode(EXPECTATION_FAILED.value());
    }

    @Test
    void givenOneLendingOverDueDate_whenAskedByLibrarian_thenReturnLending() {
        Librarian librarian = new Librarian("tommy", "tom", "unique@email.com");
        librarianRepository.addLibrarian(librarian);
        Book book = new Book("777", "casino", new Author("Napoleon", "Games"), "lucky");
        bookRepository.saveBook(book);
        Lending lending = new Lending(book.getIsbn(), member.getUniqueId());
        lending.setDueDate(LocalDate.now().minusDays(1));
        lendingRepository.addLending(lending);


        LendingDto[] lendingDtos =
                RestAssured
                        .given()
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization(librarian.getEmail(), "234"))
                        .when()
                        .port(port)
                        .get("/lendings/overdue")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(LendingDto[].class);

        assertThat(lendingDtos).contains(LendingMapper.lendingToLendingDto(lending));
    }

    @Test
    void givenOneLendingNotOverDueDate_whenAskedByLibrarian_thenReturnNoLending() {
        Librarian librarian = new Librarian("tommy", "tom", "unique@emailto.com");
        librarianRepository.addLibrarian(librarian);
        Book book = new Book("888", "casino", new Author("Napoleon", "Games"), "lucky");
        bookRepository.saveBook(book);
        Lending lending = new Lending(book.getIsbn(), member.getUniqueId());
        lendingRepository.addLending(lending);


        LendingDto[] lendingDtos =
                RestAssured
                        .given()
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization(librarian.getEmail(), "234"))
                        .when()
                        .port(port)
                        .get("/lendings/overdue")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(LendingDto[].class);

        assertThat(lendingDtos).doesNotContain(LendingMapper.lendingToLendingDto(lending));
    }

    @Test
    void givenOneLendingOverDueDate_whenAskedByMember_thenReturnUnauthorizedError() {
        Book book = new Book("999", "casino", new Author("Napoleon", "Games"), "lucky");
        bookRepository.saveBook(book);
        Lending lending = new Lending(book.getIsbn(), member.getUniqueId());
        lendingRepository.addLending(lending);

        RestAssured
                .given()
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization(member.getEmail(), "234"))
                .when()
                .port(port)
                .get("/lendings/overdue")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }


    @Test
    void whenGivenCorrectLendingId_returnTheBookCorrectly_whenOnTime_giveCorrectMessage() {
        Author author = new Author("PS", "TK");
        Book book = new Book("523", "Lent Out Book", author, "Coding Magic");
        String expectedResult =  "Book is returned on time";

        book.lent();
        bookRepository.saveBook(book);

        Lending lending = new Lending(book.getIsbn(),member.getUniqueId());
        lending.setDueDate(lending.getDueDate().minusWeeks(3));

        lendingRepository.addLending(lending);

        ReturnLendingDto returnLendingDto = new ReturnLendingDto(lending.getUniqueLendingId());

     AnswerReturnDto actualResult = RestAssured
                .given()
                .body(returnLendingDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
                .when()
                .port(port)
                .delete("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value()).extract().as(AnswerReturnDto.class);

        assertThat(actualResult.answerRequestReturnBook()).isEqualTo(expectedResult);
        assertThat(lending.isReturned()).isTrue();
    }

    @Test
    void whenGivenCorrectLendingId_returnTheBookCorrectly_whenToLate_giveCorrectMessage() {
        Author author = new Author("PS", "TK");
        Book book = new Book("123", "Lent Out Book", author, "Coding Magic");
        String expectedResult =  "The book is returned too late.";

        book.lent();
        bookRepository.saveBook(book);

        Lending lending = new Lending(book.getIsbn(),member.getUniqueId());
        lending.setDueDate(lending.getDueDate().minusWeeks(4));
        lendingRepository.addLending(lending);

        ReturnLendingDto returnLendingDto = new ReturnLendingDto(lending.getUniqueLendingId());

        AnswerReturnDto actualResult = RestAssured
                .given()
                .body(returnLendingDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
                .when()
                .port(port)
                .delete("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value()).extract().as(AnswerReturnDto.class);

        assertThat(actualResult.answerRequestReturnBook()).isEqualTo(expectedResult);
        assertThat(lending.isReturned()).isTrue();
    }


    @Test
    void returningABookWith_aLendingIdThatDoesntMatchAnyExistingLendingId() {
        Author author = new Author("PS", "TK");
        Book book = new Book("525", "Lent Out Book", author, "Coding Magic");

        book.lent();
        bookRepository.saveBook(book);

        String lendingId = "helloIShouldBe_ALendingID_ButImJust_aRandomSentenceToFailThisTest";
        Lending lending = new Lending(book.getIsbn(),member.getUniqueId());
        lending.setDueDate(lending.getDueDate().minusWeeks(4));
        lendingRepository.addLending(lending);

        System.out.println(RestAssured
                .given()
                .body(new ReturnLendingDto(lendingId))
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
                .when()
                .port(port)
                .delete("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value()).toString());
    }

    @Test
    void returningABook_withEmptyRepository() {
        String lendingId = "helloIShouldBe_ALendingID_ButImJust_aRandomSentenceToFailThisTest";

        System.out.println(RestAssured
                .given()
                .body(new ReturnLendingDto(lendingId))
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
                .when()
                .port(port)
                .delete("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value()).toString());
    }

    @Test
    void returningABookWith_aLendingIdThatIsDeleted() {
        Author author = new Author("PS", "TK");
        Book book = new Book("526", "Lent Out Book", author, "Coding Magic");

        book.setDeleted(true);
        bookRepository.saveBook(book);

        String lendingId = "helloIShouldBe_ALendingID_ButImJust_aRandomSentenceToFailThisTest";
        Lending lending = new Lending(book.getIsbn(),member.getUniqueId());
        lending.setDueDate(lending.getDueDate().minusWeeks(4));
        lendingRepository.addLending(lending);

        System.out.println(RestAssured
                .given()
                .body(new ReturnLendingDto(lendingId))
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
                .when()
                .port(port)
                .delete("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value()).toString());
    }


    @Test
    void returningABookWith_aLendingIdThatIsAlreadyReturned() {
        Author author = new Author("PS", "TK");
        Book book = new Book("527", "Lent Out Book", author, "Coding Magic");

        book.lent();
        bookRepository.saveBook(book);

        Lending lending = new Lending(book.getIsbn(),member.getUniqueId());
        lending.setDueDate(lending.getDueDate().minusWeeks(4));
        lendingRepository.addLending(lending);
        ReturnLendingDto returnLendingDto = new ReturnLendingDto(lending.getUniqueLendingId());


        System.out.println(RestAssured
                .given()
                .body(returnLendingDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
                .when()
                .port(port)
                .delete("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value()).toString());

        System.out.println(RestAssured
                .given()
                .body(returnLendingDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
                .when()
                .port(port)
                .delete("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value()).toString());
    }

    @Test
    void returningSomeoneElseBook_givesException() {
        Author author = new Author("PS", "TK");
        Book book = new Book("600", "Lent Out Book2", author, "Coding Magic");

        Address address = new Address("Sesame Street", "221B", "9900", "Leuven");
        Member member2 = new Member("NotSpeedy", "Gonzales", "not.speedy.gonzales@outlook.com", "1521", address);

        book.lent();
        bookRepository.saveBook(book);

        Lending lending = new Lending(book.getIsbn(),member2.getUniqueId());
        lending.setDueDate(lending.getDueDate().minusWeeks(4));
        lendingRepository.addLending(lending);
        ReturnLendingDto returnLendingDto = new ReturnLendingDto(lending.getUniqueLendingId());

        System.out.println(RestAssured
                .given()
                .body(returnLendingDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
                .when()
                .port(port)
                .delete("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value()).toString());
    }
}
