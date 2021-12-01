package com.team418.domain.user;

import com.team418.domain.Feature;

import java.util.List;

import static com.team418.domain.Feature.CREATE_LIBRARIAN;
import static com.team418.domain.Feature.VIEW_DELETED_BOOK;

public class Admin extends User {
    private static final List<Feature> FEATURES = List.of(CREATE_LIBRARIAN, VIEW_DELETED_BOOK);

    public Admin(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    @Override
    public boolean isAbleTo(Feature feature) {
        return FEATURES.contains(feature);
    }
}
