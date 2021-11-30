package com.team418.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InssNotUniqueException extends RuntimeException{
    public InssNotUniqueException(String message){
        super(message + " is already used.");
    }
}
