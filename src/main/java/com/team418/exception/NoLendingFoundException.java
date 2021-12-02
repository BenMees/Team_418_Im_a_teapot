package com.team418.exception;

public class NoLendingFoundException extends RuntimeException{
    public NoLendingFoundException(String lendingId) {
        super("Your requested Lending can not be found." + lendingId);
    }
}