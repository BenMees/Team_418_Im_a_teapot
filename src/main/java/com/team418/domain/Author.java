package com.team418.domain;

import java.util.Objects;

import static com.team418.services.inputvalidator.InputValidator.INPUT_VALIDATOR;

public class Author {
    private String firstName;
    private String lastName;

    public Author(String firstName, String lastName) {
        this.firstName = setFirstName(firstName);
        this.lastName = setLastName(lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String setFirstName(String firstName) {
        return INPUT_VALIDATOR.validateNoEmptyInput(firstName);
    }

    public String setLastName(String lastName) {
        return INPUT_VALIDATOR.validateNoEmptyInput(lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;
        return Objects.equals(firstName, author.firstName) && Objects.equals(lastName, author.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
