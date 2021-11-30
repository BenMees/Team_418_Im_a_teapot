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

    public void validate(String authorization, Feature feature) {
        User user = validateUserName(authorization);
        validateAccessToFeature(user, feature);
    }

    public User validateUserName(String authorization) {
        String decodeUsernamePassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String email = decodeUsernamePassword.substring(0, decodeUsernamePassword.indexOf(":"));
        User user = this.userRepository.getByEmail(email);

        if (user == null)
            throw new UnknownUserException("No user corresponds to : " + email);

        return user;
    }

    public void validateAccessToFeature(User user, Feature feature) {
        if (!user.isAbleTo(feature))
            throw new UnauthorizedException(user.getEmail() + " does not have access to " + feature.name());
    }
}
