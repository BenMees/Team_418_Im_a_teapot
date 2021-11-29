package com.team418.domain.user;

import com.team418.domain.Feature;

import java.util.ArrayList;
import java.util.List;

public class Librarian extends User{
    private static final List<Feature> FEATURES = List.of();

    public Librarian(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    @Override
    public boolean isAbleTo(Feature feature) {
        return FEATURES.contains(feature);
    }
}
