//package com.team418.repository;
//
//
//import com.team418.domain.user.Librarian;
//import com.team418.domain.user.User;
//import com.team418.exception.EmailNotUniqueException;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//class UserRepositoryTest {
//
//    UserRepository userRepository;
//
//    @BeforeEach
//    void setup() {
//        userRepository = new UserRepository();
//    }
//
//    @Test
//    void addUser_whenUserIsLibrarian_thenLibrarianIsInRepository() {
//        // GIVEN
//        Librarian librarian = new Librarian("Jon", "Snow", "jon@snow.com");
//
//        // WHEN
//        userRepository.addUser(librarian);
//        User actual = userRepository.getByEmail(librarian.getEmail());
//
//        // THEN
//        Assertions.assertThat(actual).isEqualTo(librarian);
//    }
//
//    @Test
//    void addUser_whenEmailIsNotUnique_thenThrowsEmailNotUniqueException() {
//        // GIVEN
//        Librarian librarian = new Librarian("Jon", "Snow", "jon@snow.com");
//        userRepository.addUser(librarian);
//
//        // WHEN
//        Assertions.assertThatExceptionOfType(EmailNotUniqueException.class).isThrownBy(() -> userRepository.addUser(new Librarian("Jony", "Snowy", "jon@snow.com")));
//
//    }
//
//
//}