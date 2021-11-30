package com.team418.domain.user;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;


    @BeforeEach()
    void setUp(){
        user = new Member("Dave","Navaro","dave.n@gmail.com","01019.517.29");

    }
}