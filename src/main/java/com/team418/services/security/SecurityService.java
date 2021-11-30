package com.team418.services.security;

import com.team418.domain.Feature;
import com.team418.domain.user.User;
import com.team418.repository.UserRepository;
import com.team418.exception.UnauthorizedException;
import com.team418.exception.UnknownUserException;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SecurityService {
    UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // todo only ask SS to validate (more than one thing); keep them discreet
    // public void/boolean thatThrowsErrors eg validate(String toValidate, optional Feature?) {}

    public User validateAccessToFeature(String authorization, Feature feature) {
        String decodeUsernamePassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String email = decodeUsernamePassword.split(":")[0];
        String password = decodeUsernamePassword.split(":")[1];
        User user = this.userRepository.getByEmail(email);

        if (user == null)
            throw new UnknownUserException("No user corresponds to : " + email);
        if (!user.isAbleTo(feature))
            throw new UnauthorizedException(email + " does not have access to " + feature.name());

        return user;
    }
}
