package com.team418.exception;

public class LendingIsAlreadyReturnedException extends RuntimeException {
    public LendingIsAlreadyReturnedException(){
        super("This bok has already returned");
    }
}
