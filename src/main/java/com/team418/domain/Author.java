package com.team418.domain;

import java.util.Objects;

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
        return replaceEmptyInput(firstName);
    }

    public String setLastName(String lastName) {
        return validateNoEmptyInput(lastName);
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

    /**
     * @param input The fields to validate
     * @return the input if valid, throws invalid argument exception if it isn't
     */
    public static String validateNoEmptyInput(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException();
        return input;
    }


    /**
     * @param input A field to validate that's permitted to be empty
     * @return the non-empty field, or an empty string
     */
    public static String replaceEmptyInput(String input) {
        return (input == null) ? "" : input;
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
