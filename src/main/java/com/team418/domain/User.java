package com.team418.domain;

import java.util.UUID;

public class User {
    private final String uniqueId;
    private final String firstName;
    private final String lastName;
    private final Address address;

    public User(String firstName, String lastName, Address address) {
        this.uniqueId = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
}
