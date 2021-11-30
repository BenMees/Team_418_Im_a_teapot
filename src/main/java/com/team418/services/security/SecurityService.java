package com.team418.services.security;

import com.team418.domain.Feature;
import com.team418.domain.user.User;
import com.team418.repository.AdminRepository;
import com.team418.repository.LibrarianRepository;
import com.team418.exception.UnauthorizedException;
import com.team418.exception.UnknownUserException;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SecurityService {
    LibrarianRepository librarianRepository;
    AdminRepository adminRepository;

    public SecurityService(LibrarianRepository userRepository, AdminRepository adminRepository) {
        this.librarianRepository = userRepository;
        this.adminRepository = adminRepository;
    }


    public User validateAccessToFeature(String authorization, Feature feature) {
        String decodeUsernamePassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String email = decodeUsernamePassword.split(":")[0];
        String password = decodeUsernamePassword.split(":")[1];
        //User user = this.librarianRepository.getByEmail(email);
        User user = this.adminRepository.getByEmail(email);

        if (user == null)
            throw new UnknownUserException("No user corresponds to : " + email);
        if (!user.isAbleTo(feature))
            throw new UnauthorizedException(email + " does not have access to " + feature.name());

        return user;
    }
}
