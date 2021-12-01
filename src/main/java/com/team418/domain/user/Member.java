package com.team418.domain.user;

import com.team418.domain.Feature;

import java.util.List;

import static com.team418.domain.Feature.REGISTER_NEW_LENDING;

public class Member extends User {
    private static final List<Feature> FEATURES = List.of(REGISTER_NEW_LENDING);

    private final String inss;

    public Member(String firstName, String lastName, String email, String inss) {
        super(firstName, lastName, email);
        this.inss = inss;
    }

    public String getInss() {
        return inss;
    }

    @Override
    public boolean isAbleTo(Feature feature) {
        return FEATURES.contains(feature);
    }
}
