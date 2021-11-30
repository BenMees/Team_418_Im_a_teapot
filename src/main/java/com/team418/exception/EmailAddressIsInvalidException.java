package com.team418.exception;

public class EmailAddressIsInvalidException extends RuntimeException {
    public EmailAddressIsInvalidException(String emailAddress) {
        super("The email address that you entered is not valid : " + emailAddress);
    }
}
