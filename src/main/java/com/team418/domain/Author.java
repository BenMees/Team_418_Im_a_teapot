package com.team418.domain;

import java.util.Locale;
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
        String lowerCaseFirstName = firstName.toLowerCase();
        String lowerCaseLastName = lastName.toLowerCase();
        String lowerCasePartNamesRegex = partNamesRegex.toLowerCase();
        boolean isMatching;

        Pattern pattern = Pattern.compile(lowerCasePartNamesRegex);

        isMatching = pattern.matcher(lowerCaseFirstName).matches();
        isMatching = isMatching || pattern.matcher(lowerCaseLastName).matches();
        isMatching = isMatching || pattern.matcher(lowerCaseFirstName+" "+lowerCaseLastName).matches();
        isMatching = isMatching || pattern.matcher(lowerCaseLastName+" "+lowerCaseFirstName).matches();

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
