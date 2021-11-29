package com.team418.services;


import com.team418.domain.Book;
import com.team418.repository.BookRepository;

import java.util.Map;
import java.util.UUID;

public class BookService {
    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Map<String, Book> getBooks() {
        return bookRepository.getBooks();
    }
}
