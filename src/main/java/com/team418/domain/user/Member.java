package com.team418.domain.user;

import com.team418.domain.Feature;
import com.team418.repository.MemberRepository;

public class Member extends User{

    private final String inss;

    public Member(String firstName, String lastName, String email, String inss) {
        super(firstName, lastName, email);
        this.inss = inss;
    }

    private void setInss(String inss){

    }


    public String getInss() {
        return inss;
    }

    @Override
    public boolean isAbleTo(Feature feature) {
        return false;
    }

}
