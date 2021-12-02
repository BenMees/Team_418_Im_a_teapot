package com.team418.exception;

public class NoLendingFoundException extends RuntimeException{
    public NoLendingFoundException() {
        super("Your requested Lending can not be found.");
    }
}
