package com.team418.services.security;

import com.team418.domain.Feature;
import com.team418.exception.UnauthorizedException;
import com.team418.exception.UnknownUserException;
import com.team418.repository.AdminRepository;
import com.team418.repository.LibrarianRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


class SecurityServiceTest {

    SecurityService securityService;

    @BeforeEach
    void setup() {
        AdminRepository adminRepository = new AdminRepository();
        LibrarianRepository librarianRepository = new LibrarianRepository();
        securityService = new SecurityService(librarianRepository, adminRepository);
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
        String authorization = "Basic " + Base64.getEncoder().encodeToString("jon@snow.com:password".getBytes(StandardCharsets.UTF_8));

        // THEN
        Assertions.assertThatExceptionOfType(UnknownUserException.class).isThrownBy(() -> securityService.validate(authorization, Feature.REGISTER_NEW_BOOK));
    }

}