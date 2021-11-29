package com.team418.domain.user;

import com.team418.domain.Feature;

public class Member extends User{

    private final String inss;

    public Member(String firstName, String lastName, String email, String inss) {
        super(firstName, lastName, email);
        this.inss = inss;
    }

    @Override
    public boolean isAbleTo(Feature feature) {
        return false;
    }

}
