package com.team418.domain;

import java.util.UUID;

public class Book {
    private final UUID uniqueId;
    private final String isbn;
    private final String title;
    private final Author author;
    private final String summary;

    public Book(UUID uniqueId, String isbn, String title, Author author, String summary) {
        this.uniqueId = uniqueId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }
}
