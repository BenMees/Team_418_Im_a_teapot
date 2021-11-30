package com.team418.repository;

import com.team418.domain.user.Librarian;
import com.team418.domain.user.Member;
import com.team418.exception.EmailAddressIsInvalidException;
import com.team418.exception.EmailNotUniqueException;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.fail;

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
    void whenRepositry_HasNoLibrarians_weGetEmptyMapBack() {
        Assertions.assertThat(librarianRepository.getLibrarians().size()).isEqualTo(0);
    }

    @Test
    void whenGettingAllLibrarians_weGettheTwoLibrarian() {
        librarians.put(librarian1.getUniqueId(), librarian1);
        librarians.put(librarian2.getUniqueId(), librarian2);

        librarianRepository.addLibrarian(librarian1);
        librarianRepository.addLibrarian(librarian2);

        Assertions.assertThat(librarianRepository.getLibrarians()).isEqualTo(librarians);
    }

    @Test
    void when_givingAnInvalidEmail_ThrowException() {
        String expectedMessage = "The email address that you entered is not valid : ann.couwbe-outlook.com";
        try {
            new Librarian("Ann", "Cauwberg", "ann.couwbe-outlook.com");
            fail();
        } catch (EmailAddressIsInvalidException exception) {
            Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
        }
    }

    @Test
    void If_MemberWithSameEmail_AndDifferentInss_ThrowsAnException(){
        String expectedMessage = librarian1.getEmail() + " is already used.";
        Librarian libarrian3 = new Librarian(librarian1.getFirstName(), librarian1.getLastName(), librarian1.getEmail());
        try{
            librarianRepository.addLibrarian(librarian1);
            librarianRepository.addLibrarian(libarrian3);
            Assert.fail();
        }catch(EmailNotUniqueException e) {
            Assertions.assertThat(e.getMessage().equals(expectedMessage));
        }
    }
}

