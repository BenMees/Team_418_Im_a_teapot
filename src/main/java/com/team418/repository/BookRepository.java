package com.team418.repository;

import com.team418.domain.Author;
import com.team418.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookRepository {
    private final Map<String, Book> books;

    public BookRepository() {
        books = new ConcurrentHashMap<>();
    }

    public Map<String, Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.put(book.getUniqueId(),book);
    }
}
