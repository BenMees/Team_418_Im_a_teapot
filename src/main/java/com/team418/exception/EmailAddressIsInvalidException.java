package com.team418.exception;

public class EmailAddressIsInvalidException extends RuntimeException {
    public EmailAddressIsInvalidException() {
        super("The email address that you entered is not valid.");
    }
}
