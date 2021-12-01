package com.team418.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        return this.firstName = INPUT_VALIDATOR.replaceEmptyInput(firstName);
    }

    public String setLastName(String lastName) {
        return this.lastName = INPUT_VALIDATOR.validateNoEmptyInput(lastName);
    }

    public boolean firstNameORLastNameCorresponding(String partNamesRegex) {
        boolean isMatching;
        Pattern pattern = Pattern.compile(partNamesRegex);

        isMatching = pattern.matcher(firstName).matches();
        isMatching = isMatching || pattern.matcher(lastName).matches();
        isMatching = isMatching || pattern.matcher(firstName+" "+lastName).matches();
        isMatching = isMatching || pattern.matcher(lastName+" "+firstName).matches();

        return isMatching;
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
