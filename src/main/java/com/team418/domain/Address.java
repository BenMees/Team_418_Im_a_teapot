package com.team418.domain;

public class Address {
    private final String streetName;
    private final String houseNumber;
    private final String postcalCode;
    private final String city;

    public Address(String streetName, String houseNumber, String postcalCode, String city) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postcalCode = postcalCode;
        this.city = city;
    }
}
