package com.team418.domain.user;

import com.team418.exception.EmailAddressIsInvalidException;
import com.team418.domain.Feature;
import com.team418.services.inputvalidator.InputValidator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;
import java.util.UUID;

public abstract class User {
    private final String uniqueId;
    private final String firstName;
    private final String lastName;
    private String email;
    private List<Feature> featureList;

    public User(String firstName, String lastName, String email, List<Feature> featureList) {
        this.featureList = featureList;
        setEmail(email);
        this.uniqueId = UUID.randomUUID().toString();
        this.firstName = InputValidator.INPUT_VALIDATOR.validateNoEmptyInput(firstName);
        this.lastName = InputValidator.INPUT_VALIDATOR.validateNoEmptyInput(lastName);
    }

    public boolean isAbleTo(Feature feature) {
        return featureList.contains(feature);
    }

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
