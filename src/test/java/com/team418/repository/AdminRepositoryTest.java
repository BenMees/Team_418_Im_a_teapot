package com.team418.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class AdminRepositoryTest {

    @Test
    void givenStartUpNothingness_whenStartingUp_theresOneDefaultAdmin() {
        AdminRepository adminRepository = new AdminRepository();
        Assertions.assertThat(1).isEqualTo(adminRepository.getAllAdmins().size());
    }
}