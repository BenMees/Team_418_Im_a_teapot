package com.team418.api.book;


import com.team418.Utility;
import com.team418.api.book.dto.BookDto;
import com.team418.api.lending.dto.CreateLendingDto;
import com.team418.api.lending.dto.LendingDto;
import com.team418.domain.Address;
import com.team418.domain.Author;
import com.team418.domain.Book;
import com.team418.domain.user.Member;
import com.team418.repository.AdminRepository;
import com.team418.repository.BookRepository;
import com.team418.repository.LendingRepository;
import com.team418.repository.MemberRepository;
import com.team418.services.LendingService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LendingControllerTest {

    @Value("${server.port}")
    private int port;
    private final LendingService lendingService;
    private final BookRepository bookRepository;
    private final LendingRepository lendingRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public LendingControllerTest(MemberRepository memberRepository, LendingService lendingService, BookRepository bookRepository, LendingRepository lendingRepository) {
        this.lendingService = lendingService;
        this.bookRepository = bookRepository;
        this.lendingRepository = lendingRepository;
        this.memberRepository = memberRepository;
    }

    @BeforeAll
    public void setup() {
        Address address = new Address("Sesame Street","221B","9900","Leuven");
        Author author = new Author("J","K");
        Book book = new Book("134567","Harry Potter", author,"Magic");
        Member member = new Member("Speedy","Gonzales","speedy.gonzales@outlook.com","789456",address);
        bookRepository.saveBook(book);
        memberRepository.addMember(member);
    }

//   @Test
//   void createLending_givenALendingToCreate_thenTheNewlyCreatedLendingIsSavedAndReturned() {
//       CreateLendingDto createLendingDto = new CreateLendingDto("134567");
//
//       LendingDto lendingDto =
//               RestAssured
//                       .given()
//                       .body(createLendingDto)
//                       .accept(JSON)
//                       .contentType(JSON)
//                       .header("Authorization", Utility.generateBase64Authorization("speedy.gonzales@outlook.com", "234"))
//                       .when()
//                       .port(port)
//                       .post("/lendings")
//                       .then()
//                       .assertThat()
//                       .statusCode(HttpStatus.CREATED.value())
//                       .extract()
//
//                       .as(LendingDto.class);
//   }

}
