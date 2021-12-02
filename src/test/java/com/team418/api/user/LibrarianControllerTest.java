package com.team418.api.user;

import com.team418.Utility;
import com.team418.api.user.dto.*;
import com.team418.domain.user.*;
import com.team418.repository.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LibrarianControllerTest {

    @Value("${server.port}")
    private int port;
    private final LibrarianRepository librarianRepository;
    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;
    private CreateLibrarianDto createLibrarianDto;
    private Librarian librarian;
    private Member member;
    private Admin admin;

    @Autowired
    LibrarianControllerTest(LibrarianRepository librarianRepository, AdminRepository adminRepository, MemberRepository memberRepository) {
        this.librarianRepository = librarianRepository;
        this.adminRepository = adminRepository;
        this.memberRepository = memberRepository;
    }

    @BeforeAll
    void setUp() {
        librarian = new Librarian("libr", "rian", "libr@rian.com");
        librarianRepository.addLibrarian(librarian);

        member = new Member("pay2", "win", "pay2@win.com", "inss");
        memberRepository.addMember(member);

        admin = new Admin("ad", "min", "funny@min");
        adminRepository.addNewAdmin(admin);

        createLibrarianDto = new CreateLibrarianDto("Tom", "Tom", "t@m.tom");
    }

    @Test
    void givenLibrarianRepo_whenAddingLibrarianAsAdmin_thenRepoContainsLibrarian() {
        createLibrarianDto = new CreateLibrarianDto("Tom2", "Tom2", "t@m2.tom");

        LibrarianDto librarianDto = RestAssured
                .given()
                .body(createLibrarianDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization(admin.getEmail(), "admin"))
                .when()
                .port(port)
                .post("/librarians")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(LibrarianDto.class);

        assertThat(librarianDto.id()).isNotBlank();
        assertThat(librarianDto.firstName()).isEqualTo(createLibrarianDto.firstName());
        assertThat(librarianDto.lastName()).isEqualTo(createLibrarianDto.lastName());
        assertThat(librarianDto.email()).isEqualTo(createLibrarianDto.email());
    }

    @Test
    void givenLibrarianRepo_whenAddingLibrarianAsMember_thenRepoDoesNotContainLibrarian() {
        RestAssured
                .given()
                .body(createLibrarianDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization(member.getEmail(), "admin"))
                .when()
                .port(port)
                .post("/librarians")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void givenLibrarianRepo_whenAddingLibrarianAsLibrarian_thenRepoDoesNotContainLibrarian() {
        RestAssured
                .given()
                .body(createLibrarianDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization(librarian.getEmail(), "admin"))
                .when()
                .port(port)
                .post("/librarians")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void givenLibrarianRepo_whenAddingLibrarianNotLoggedIn_thenRepoDoesNotContainLibrarian() {
        RestAssured
                .given()
                .body(createLibrarianDto)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/librarians")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenLibrarianRepo_whenAddingExistingLibrarian_thenRepoDoesNotAddAgain() {
        RestAssured
                .given()
                .body(createLibrarianDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization(admin.getEmail(), "admin"))
                .when()
                .port(port)
                .post("/librarians")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured
                .given()
                .body(createLibrarianDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization(admin.getEmail(), "admin"))
                .when()
                .port(port)
                .post("/librarians")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());

        // 2 Because we initialise one (beforeAll), and add one
        assertThat(2).isEqualTo(librarianRepository.getLibrarians().size());
    }
}