package com.team418.exception;

public class MemberNotFoundByInssException extends RuntimeException {
    public MemberNotFoundByInssException(String inss) {
        super("The member with an INSS number: " + inss + " does not excist");
    }
}
