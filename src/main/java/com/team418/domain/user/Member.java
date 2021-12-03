package com.team418.domain.user;

import com.team418.domain.Address;
import com.team418.domain.Feature;
import com.team418.services.inputvalidator.InputValidator;

import java.util.List;
import static com.team418.domain.Feature.REGISTER_NEW_LENDING;
import static com.team418.domain.Feature.RETURN_lENDED_BOOK;

public class Member extends User {
    private static final List<Feature> FEATURES = List.of(REGISTER_NEW_LENDING, RETURN_lENDED_BOOK);
    private final String inss;
    private final Address address;

    public Member(String firstName, String lastName, String email, String inss, Address address) {
        super(firstName, lastName, email);
        this.inss = inss;
        InputValidator.INPUT_VALIDATOR.validateNoEmptyInput(address.city());
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public String getInss() {
        return inss;
    }

    @Override
    public boolean isAbleTo(Feature feature) {
        return FEATURES.contains(feature);
    }
}
