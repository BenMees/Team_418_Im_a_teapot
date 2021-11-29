package com.team418.repository;

import com.team418.domain.Book;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BookRepository {
    private final Map<UUID, Book> books;

    public BookRepository() {
        books = new ConcurrentHashMap<>();
    }
}
