package com.team418.exception;

public class NoBookAvailableForNowException extends RuntimeException{
    public NoBookAvailableForNowException(String bookTitle){
        super("The book " + bookTitle + " is currently not available, please ask a librarian for additional information.");
    }
}
