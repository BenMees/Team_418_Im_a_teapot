package com.team418.services.security;

import com.team418.domain.Feature;
import com.team418.domain.user.User;
import com.team418.repository.AdminRepository;
import com.team418.repository.LibrarianRepository;
import com.team418.exception.UnauthorizedException;
import com.team418.exception.UnknownUserException;
import com.team418.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SecurityService {
    private final LibrarianRepository librarianRepository;
    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;

    public SecurityService(LibrarianRepository userRepository, AdminRepository adminRepository, MemberRepository memberRepository) {
        this.librarianRepository = userRepository;
        this.adminRepository = adminRepository;
        this.memberRepository = memberRepository;
    }

    public void validate(String authorization, Feature feature) {
        User user = validateUserName(authorization);
        validateAccessToFeature(user, feature);
    }

    private User validateUserName(String authorization) {
        String decodeUsernamePassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String email = decodeUsernamePassword.substring(0, decodeUsernamePassword.indexOf(":"));

        //was userRepository
        User user = this.adminRepository.getByEmail(email);
        if (user == null) {
            user = this.librarianRepository.getByEmail(email);
        }

        if (user == null) {
            user = this.memberRepository.getByEmail(email);
        }

        if (user == null)
            throw new UnknownUserException("No user corresponds to : " + email);

        return user;
    }

    private void validateAccessToFeature(User user, Feature feature) {
        if (!user.isAbleTo(feature))
            throw new UnauthorizedException(user.getEmail() + " does not have access to " + feature.name());
    }
}
