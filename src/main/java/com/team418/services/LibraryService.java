package com.team418.services;

import com.team418.domain.user.Librarian;
import com.team418.repository.LibrarianRepository;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {

    private final LibrarianRepository librarianRepository;

    public LibraryService(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    public void addLibrarian(Librarian librarian){
        librarianRepository.addLibrarian(librarian);
    }
}
