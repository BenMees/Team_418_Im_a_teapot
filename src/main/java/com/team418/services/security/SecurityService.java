package com.team418.services.security;

import com.team418.domain.Feature;
import com.team418.domain.user.User;
import com.team418.repository.UserRepository;
import com.team418.services.security.exception.UnauthorizedException;
import com.team418.services.security.exception.UnknownUserException;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SecurityService {
    UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
