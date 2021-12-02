package com.team418.exception;

public class MoreThanOneIsbnMatchException extends RuntimeException {
    public MoreThanOneIsbnMatchException(String isbn, int amountOfMatches) {
        super("The ISBN number: " + isbn + " has " + amountOfMatches + " Matches. It can only have 1");
    }
}
