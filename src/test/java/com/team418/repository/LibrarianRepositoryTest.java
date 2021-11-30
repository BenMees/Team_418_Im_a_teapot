package com.team418.repository;

import com.team418.domain.user.Librarian;
import com.team418.exception.EmailAddressIsInvalidException;
import com.team418.exception.EmailNotUniqueException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LibrarianRepositoryTest {

    private Librarian librarian1;
    private Librarian librarian2;
    private LibrarianRepository librarianRepository;
    private Map<String, Librarian> librarians;


    @BeforeEach
    void setUp() {
        librarians = new ConcurrentHashMap<>();
        librarianRepository = new LibrarianRepository();
        librarian1 = new Librarian("Els", "Deschepenen", "els.deschepenen@outlook.com");
        librarian2 = new Librarian("Ann", "Cauwberg", "ann.couwberg@outlook.com");
    }

    @Test
    void whenRepository_HasNoLibrarians_weGetEmptyMapBack() {
        Assertions.assertThat(librarianRepository.getLibrarians()).isEmpty();
    }

    @Test
    void whenGettingAllLibrarians_weGetTheTwoLibrarian() {
        librarians.put(librarian1.getUniqueId(), librarian1);
        librarians.put(librarian2.getUniqueId(), librarian2);

        librarianRepository.addLibrarian(librarian1);
        librarianRepository.addLibrarian(librarian2);

        Assertions.assertThat(librarianRepository.getLibrarians()).isEqualTo(librarians);
    }

    @Test
    void when_givingAnInvalidEmail_ThrowException() {
        String expectedMessage = "The email address that you entered is not valid : ann.couwbe-outlook.com";
        Assertions.assertThatExceptionOfType(EmailAddressIsInvalidException.class)
                .isThrownBy(() -> new Librarian("Ann", "Cauwberg", "ann.couwbe-outlook.com"))
                .withMessage(expectedMessage);
    }

    @Test
    void If_MemberWithSameEmail_AndDifferentInss_ThrowsAnException(){
        Librarian librarian3 = new Librarian(librarian1.getFirstName(), librarian1.getLastName(), librarian1.getEmail());
        Assertions.assertThatExceptionOfType(EmailNotUniqueException.class).isThrownBy(() -> {
            librarianRepository.addLibrarian(librarian1);
            librarianRepository.addLibrarian(librarian3);
        });
    }
}

