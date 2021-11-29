package com.team418.domain;

import java.util.UUID;

public class User {
    private final UUID uniqueId;
    private final String firstName;
    private final String lastName;
    private final Address address;

    public User(UUID uniqueId, String firstName, String lastName, Address address) {
        this.uniqueId = uniqueId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
}
