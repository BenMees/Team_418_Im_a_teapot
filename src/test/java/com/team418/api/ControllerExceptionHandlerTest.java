package com.team418.api;

import com.team418.repository.AdminRepository;
import org.junit.jupiter.api.BeforeAll;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ControllerExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void emailAddressInvalidException() throws Exception{

        String wrongEmail ="blablab";
        String content = "{\n" +
                "    \"firstName\":\"Anne\",\n" +
                "    \"lastName\":\"Verbergen\",\n" +
                "    \"email\": \""+ wrongEmail +"\"\n" +
                "}";

        this.mockMvc.perform(post("/librarians")
                .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,"Basic ZGVmYXVsdEBzd2l0Y2hmdWxseS5jb206" )
                        .content(content)).andExpect(status().reason(containsString("The email address that you entered is not valid : " + wrongEmail)));
    }

//    @Test
//    void unauthorizedHandler() {
//    }
//
//    @Test
//    void unknownUserHandler() {
//    }
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
//    @Test
//    void noBookFoundException() {
//    }
//
//    @Test
//    void noMemberFoundException() {
//    }
//
//    @Test
//    void bookIsNotAvailable() {
//    }
//
//    @Test
//    void isbnAlreadyPresent() {
//    }
//
//    @Test
//    void lendingIsAlreadyReturnedException() {
//    }
}