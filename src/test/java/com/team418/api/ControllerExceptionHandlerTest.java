package com.team418.api;

import com.team418.domain.Author;
import com.team418.domain.Book;
import com.team418.domain.user.Librarian;
import com.team418.repository.BookRepository;
import com.team418.repository.LibrarianRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ControllerExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

//    @Autowired
//    private MemberRepository memberRepository;

    @Test
    void emailAddressInvalidException() throws Exception {

        String wrongEmail = "blablab";
        String contentLibrarian = "{\n" +
                "    \"firstName\":\"Anne\",\n" +
                "    \"lastName\":\"Verbergen\",\n" +
                "    \"email\": \"" + wrongEmail + "\"\n" +
                "}";


        this.mockMvc.perform(post("/librarians")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Basic ZGVmYXVsdEBzd2l0Y2hmdWxseS5jb206")
                .content(contentLibrarian))



                .andExpect(status().reason(containsString("The email address that you entered is not valid : " + wrongEmail)));
    }

    @Test
    void unauthorizedHandler() throws Exception {
        String bookContent = """
                {
                    "isbn" : "12345",
                    "title" : "testTitle",
                    "author" :\s
                        {"firstName" : "testFirstNameAuthor",
                        "lastName" : "testLastNameAuthor"}
                ,
                    "summary" : "testSummary"
                }""";
        bookRepository = new BookRepository();
        bookRepository.saveBook(new Book("12345", "title", new Author("Son", "Goku"), "Lots of action"));

        librarianRepository = new LibrarianRepository();
        Librarian librarian = new Librarian("HIP", "HOP", "cheetos@africos.com");
        librarianRepository.addLibrarian(librarian);


        this.mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Basic ZGVmYXVsdEBzd2l0Y2hmdWxseS5jb206").content(bookContent))
                .andExpect(status().reason(containsString("default@switchfully.com does not have access to REGISTER_NEW_BOOK")));
    }

    //
    @Test
    void unknownUserHandler() throws Exception {

        String contentLibrarian = """
                {
                    "firstName":"Anne",
                    "lastName":"Verbergen",
                    "email": "cheetah@africa.com"
                }""";

        this.mockMvc.perform(post("/librarians")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Basic Y2hlZXRhaEBhZnJpY2EuY29tOg==")
                .content(contentLibrarian)).andExpect(status().reason(containsString("No user corresponds to : cheetah@africa.com")));
    }

    //
//    @Test
//    void illegalArgumentHandler() {
//    }
//
//    @Test
//    void inssNotUniqueException() {
//    }
//
//    @Test
//    void moreThenOneIsbnMatchException() {
//    }
//
    @Test
    void noBookFoundException() throws Exception {
        this.mockMvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .param("isbnContain", "randomISBN")).andExpect(status().reason(containsString("No book whit the ISBN number: randomISBN  is found.")));
    }

    //
//    @Test
//    void noMemberFoundException() {
//    }
//
//    @Test
//    void bookIsNotAvailable() {
//    }
//
    @Test
    void isbnAlreadyPresent() throws Exception {
        String bookContent = """
                {
                    "isbn" : "12345",
                    "title" : "testTitle",
                    "author" :\s
                        {"firstName" : "testFirstNameAuthor",
                        "lastName" : "testLastNameAuthor"}
                ,
                    "summary" : "testSummary"
                }""";

        bookRepository.saveBook(new Book("12345", "title", new Author("Son", "Goku"), "Lots of action"));

        Librarian libr = new Librarian("HIP", "HOP", "cheetos@africos.com");
        librarianRepository.addLibrarian(libr);


        this.mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Basic Y2hlZXRvc0BhZnJpY29zLmNvbTo=").content(bookContent))
                .andExpect(status().reason(containsString("The Isbn value you entered : 12345 already matches an other book.")));
    }

//    @Test
//    void lendingIsAlreadyReturnedException() throws Exception {
//        Book book = new Book("666", "title2", new Author("Say", "So"), "Lots of Music");
//        bookRepository.saveBook(book);
//
//        Member member = new Member("Karl", "Gauss", "karl.gauss@gmail.com", "987-223-112", null);
//        memberRepository.addMember(member);
//
//        this.mockMvc.perform(delete("/lendings")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header(HttpHeaders.AUTHORIZATION, "Basic a2FybC5nYXVzc0BnbWFpbC5jb206").content(book.getUniqueId()))
//                .andExpect(status().reason(containsString("")));
//    }
}
