package com.team418.exception;

public class NoBookFoundWithIsbnException extends RuntimeException{
    public NoBookFoundWithIsbnException(String isbn) {
        super("No book whit the ISBN number: " + isbn + "  is found.");
    }
}
