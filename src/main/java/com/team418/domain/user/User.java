package com.team418.domain.user;

import com.team418.exception.EmailAddressIsInvalidException;
import com.team418.domain.Feature;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.UUID;

public abstract class User {
    private final String uniqueId;
    private final String firstName;
    private final String lastName;
    private String email;

    public User(String firstName, String lastName, String email) {
        setEmail(email);
        this.uniqueId = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public abstract boolean isAbleTo(Feature feature);

    public String getEmail() {
        return email;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setEmail(String email) {
        if (!isValidEmailAddress(email)) {
            throw new EmailAddressIsInvalidException(email);
        }
        this.email = email;
    }

    private boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
