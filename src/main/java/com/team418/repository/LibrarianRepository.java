package com.team418.repository;

import com.team418.domain.user.Librarian;
import com.team418.domain.user.User;
import com.team418.exception.EmailNotUniqueException;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class LibrarianRepository {
    private final Map<String, Librarian> librarians;

    public LibrarianRepository() {
        librarians = new ConcurrentHashMap<>();
    }

    public User getByEmail(String email) {
        return librarians.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }

    public void addLibrarian(Librarian librarian) {
        assertEmailIsUnique(librarian.getEmail());
        librarians.put(librarian.getUniqueId(), librarian);
    }

    private void assertEmailIsUnique(String email) {
        librarians.values().forEach(user -> {
            if (user.getEmail().equals(email))
                throw new EmailNotUniqueException(email + " is already used.");
        });
    }

    public Map<String,Librarian> getLibrarians(){
        return librarians;
    }
}