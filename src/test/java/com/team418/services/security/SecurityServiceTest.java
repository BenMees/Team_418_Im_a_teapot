package com.team418.services.security;

import com.team418.Utility;
import com.team418.domain.Feature;
import com.team418.exception.UnauthorizedException;
import com.team418.exception.UnknownUserException;
import com.team418.repository.AdminRepository;
import com.team418.repository.LibrarianRepository;
import com.team418.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class SecurityServiceTest {

    SecurityService securityService;

    @BeforeEach
    void setup() {
        AdminRepository adminRepository = new AdminRepository();
        LibrarianRepository librarianRepository = new LibrarianRepository();
        MemberRepository memberRepository = new MemberRepository();
        securityService = new SecurityService(librarianRepository, adminRepository, memberRepository);
    }

    @Test
    void validate_givenAuthorizationThatIsValidAndCorrectAccess_thenNoExceptionIsThrown() {
        // GIVEN
        String authorization = "Basic ZGVmYXVsdEBzd2l0Y2hmdWxseS5jb206Ym9ucXNkZg==";

        // WHEN
        Assertions.assertThatNoException().isThrownBy(() -> securityService.validate(authorization, Feature.CREATE_LIBRARIAN));
    }

    @Test
    void validate_givenAuthorizationThatIsValidAndIncorrectAccess_thenNoExceptionIsThrown() {
        // GIVEN
        String authorization = "Basic ZGVmYXVsdEBzd2l0Y2hmdWxseS5jb206Ym9ucXNkZg==";

        // THEN
        Assertions.assertThatExceptionOfType(UnauthorizedException.class).isThrownBy(() -> securityService.validate(authorization, Feature.REGISTER_NEW_BOOK));
    }

    @Test
    void validate_givenAuthorizationThatIsInvalidAndCorrectAccess_thenNoExceptionIsThrown() {
        // GIVEN
        String authorization = Utility.generateBase64Authorization("jon@snow.com", "1234");

        // THEN
        Assertions.assertThatExceptionOfType(UnknownUserException.class).isThrownBy(() -> securityService.validate(authorization, Feature.REGISTER_NEW_BOOK));
    }

}