package com.team418.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class EmailNotUniqueException extends RuntimeException{
    public EmailNotUniqueException(String message) {
        super(message);
    }
}
