package com.team418.exception;

public class EmailAddressIsInvalidException extends RuntimeException {
    public EmailAddressIsInvalidException(String emailAdress) {
        super("The email address that you entered is not valid : " + emailAdress);
    }
}
