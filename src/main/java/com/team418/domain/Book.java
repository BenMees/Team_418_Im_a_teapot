package com.team418.domain;

import java.util.Objects;
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

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(uniqueId, book.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId);
    }
}
