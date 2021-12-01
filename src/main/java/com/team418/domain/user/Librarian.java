package com.team418.domain.user;

import com.team418.domain.Feature;

import java.util.List;

import static com.team418.domain.Feature.REGISTER_NEW_BOOK;
import static com.team418.domain.Feature.UPDATE_BOOK;

public class Librarian extends User {
    private static final List<Feature> FEATURES = List.of(REGISTER_NEW_BOOK, UPDATE_BOOK);

    public Librarian(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    @Override
    public boolean isAbleTo(Feature feature) {
        return FEATURES.contains(feature);
    }
}
