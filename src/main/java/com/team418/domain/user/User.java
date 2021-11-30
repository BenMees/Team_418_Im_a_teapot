package com.team418.domain.user;

import com.team418.domain.Feature;
import io.swagger.v3.oas.models.media.EmailSchema;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.util.UUID;

public abstract class User {
    private final String uniqueId;
    private final String firstName;
    private final String lastName;
    private final String email;


    public User(String firstName, String lastName, String email) {
        this.email = email;
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
}
