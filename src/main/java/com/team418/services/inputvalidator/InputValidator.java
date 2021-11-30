package com.team418.services.inputvalidator;

public enum InputValidator {
    INPUT_VALIDATOR;
    /**
     * @param input The fields to validate
     * @return the input if valid, throws invalid argument exception if it isn't
     */
    public String validateNoEmptyInput(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException();
        return input;
    }


    /**
     * @param input A field to validate that's permitted to be empty
     * @return the non-empty field, or an empty string
     */
    public String replaceEmptyInput(String input) {
        return (input == null) ? "" : input;
    }
}
