package com.team418.api.lending;


import com.team418.Utility;
import com.team418.api.lending.dto.CreateLendingDto;
import com.team418.api.lending.dto.LendingDto;
import com.team418.domain.Address;
import com.team418.domain.Author;
import com.team418.domain.Book;
import com.team418.domain.user.Member;
import com.team418.repository.BookRepository;
import com.team418.repository.MemberRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

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
        Address address = new Address("Sesame Street","221B","9900","Leuven");
        Author author = new Author("J","K");
        Book book = new Book(ISBN,"Harry Potter", author,"Magic");
        member = new Member("Speedy","Gonzales","speedy.gonzales@outlook.com","789456",address);
        bookRepository.saveBook(book);
        memberRepository.addMember(member);
    }

   @Test
   void createLending_givenALendingToCreate_thenTheNewlyCreatedLendingIsSavedAndReturned() {
       CreateLendingDto createLendingDto = new CreateLendingDto(ISBN);

       System.out.println("\u001B[35m" + createLendingDto);


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
                       .statusCode(HttpStatus.CREATED.value())
                       .extract()
                       .as(LendingDto.class);

       System.out.println(createLendingDto.isbn());
       System.out.println(lendingDto.bookIsbn());
       System.out.println("=========================");

       assertThat(lendingDto.bookIsbn()).isEqualTo(createLendingDto.isbn());
       assertThat(lendingDto.memberId()).isEqualTo(member.getUniqueId());
       assertThat(lendingDto.dueDate()).isEqualTo(LocalDate.now().plusWeeks(3));
   }
}
