package com.team418.domain;

import java.util.Objects;
import java.util.UUID;

public class Book {
    private final String uniqueId;
    private final String isbn;
    private String title;
    private Author author;
    private String summary;

    public Book(String isbn, String title, Author author, String summary) {
        this.uniqueId = UUID.randomUUID().toString();
        this.isbn = isbn;
        this.title = setTitle(title);
        this.author = author;
        this.summary = setSummary(summary);
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

    public String setTitle(String title) {
        return this.title = validateNoEmptyInput(title);
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String setSummary(String summary) {
        return this.summary = replaceEmptyInput(summary);
    }

    /**
     * @param input The fields to validate
     * @return the input if valid, throws invalid argument exception if it isn't
     */
    public static String validateNoEmptyInput(String input) {
        if (input == null || input.isBlank())
            throw new IllegalArgumentException();
        return input;
    }


    /**
     * @param input A field to validate that's permitted to be empty
     * @return the non-empty field, or an empty string
     */
    public static String replaceEmptyInput(String input) {
        return (input == null) ? "" : input;
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
