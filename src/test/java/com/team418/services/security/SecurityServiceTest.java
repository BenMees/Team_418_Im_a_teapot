package com.team418.services.security;

import com.team418.domain.Feature;
import com.team418.domain.user.Librarian;
import com.team418.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class SecurityServiceTest {

    SecurityService securityService;

    @BeforeEach
    void setup() {
        UserRepository userRepository = new UserRepository();
        securityService = new SecurityService(userRepository);
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

        // WHEN

        // THEN
        securityService.validate(authorization, Feature.CREATE_LIBRARIAN);
    }

}