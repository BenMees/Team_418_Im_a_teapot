package com.team418.exception;

public class CreateBookWithAlreadyExistingIsbnException extends RuntimeException{
    public CreateBookWithAlreadyExistingIsbnException(String isbn){
        super("The Isbn value you entered : " + isbn + " already matches an other book.");
    }
}
