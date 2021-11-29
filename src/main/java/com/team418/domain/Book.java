package com.team418.domain;

import java.util.UUID;

public class Book {
    private final String uniqueId;
    private final String isbn;
    private final String title;
    private final Author author;
    private final String summary;

    public Book(String isbn, String title, Author author, String summary) {
        this.uniqueId = UUID.randomUUID().toString();
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
    }

    public String getUniqueId() {
        return uniqueId;
    }
}
