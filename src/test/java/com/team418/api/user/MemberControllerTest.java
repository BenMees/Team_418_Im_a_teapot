package com.team418.api.user;

import com.team418.Utility;
import com.team418.api.user.dto.MemberDto;
import com.team418.domain.Address;
import com.team418.domain.user.*;
import com.team418.repository.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static com.team418.api.user.MemberMapper.memberToDtoViewingPurposes;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberControllerTest {

    @Value("${server.port}")
    private int port;
    private final LibrarianRepository librarianRepository;
    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;
    private Librarian librarian;
    private Member member;
    private Admin admin;

    @Autowired
    MemberControllerTest(LibrarianRepository librarianRepository, AdminRepository adminRepository, MemberRepository memberRepository) {
        this.librarianRepository = librarianRepository;
        this.adminRepository = adminRepository;
        this.memberRepository = memberRepository;
    }

    @BeforeAll
    void setUp() {
        librarian = new Librarian("libr", "rian", "libr@rian.com2");
        librarianRepository.addLibrarian(librarian);

        Address address = new Address("Sesame Street", "221B", "9900", "Leuven");

        member = new Member("pay2", "win", "pay2@win.com2", "isssnss", address);
        memberRepository.addMember(member);

        admin = new Admin("ad", "min", "funny@min2");
        adminRepository.addNewAdmin(admin);
    }

    @Test
    void givenFilledRepoWithMembers_whenGettingThemAsAnAdmin_thenDisplayThemAll() {
        Member tommy = new Member("Tommy", "D", "tokg@ghbi", "4165",
                new Address("d", "10A", "9050", "Ghent"));
        Member tommy2 = new Member("Tommy2", "S", "tokfdgg@ghbi", "98+4",
                new Address("d", "10A", "9050", "Ghent"));

        memberRepository.addMember(tommy);
        memberRepository.addMember(tommy2);
        int startingSize = memberRepository.getMembers().size();

        MemberDto[] memberDtos =
                RestAssured.given()
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization(admin.getEmail(), "admin"))
                        .when()
                        .port(port)
                        .get("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(MemberDto[].class);

        assertThat(startingSize).isEqualTo(memberDtos.length);
        assertThat(memberDtos).contains(memberToDtoViewingPurposes(tommy), memberToDtoViewingPurposes(tommy2));
    }


    @Test
    void givenFilledRepoWithMembers_whenGettingThemAsAMember_thenDoNotDisplayThemAll() {
        RestAssured.given()
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization(member.getEmail(), "admin"))
                .when()
                .port(port)
                .get("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }


    @Test
    void givenFilledRepoWithMembers_whenGettingThemAsALibrarian_thenDoNotDisplayThemAll() {
        RestAssured.given()
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization(librarian.getEmail(), "admin"))
                .when()
                .port(port)
                .get("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }


    @Test
    void givenFilledRepoWithMembers_whenGettingThemAsNonUser_thenDoNotDisplayThemAll() {
        RestAssured.given()
                .contentType(JSON)
                .when()
                .port(port)
                .get("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    void givenFilledMemberRepo_whenGettingMembers_thenDontShareInss() {
        MemberDto[] memberDtos =
                RestAssured.given()
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization(admin.getEmail(), "admin"))
                        .when()
                        .port(port)
                        .get("/members")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(MemberDto[].class);

        for(MemberDto member : memberDtos) {
            assertThat(member.inss()).isNullOrEmpty();
        }
    }
}