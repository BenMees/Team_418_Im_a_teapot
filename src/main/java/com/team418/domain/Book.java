package com.team418.domain;

import com.team418.exception.NoBookAvailableForNowException;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.team418.services.inputvalidator.InputValidator.INPUT_VALIDATOR;

public class Book {
    private final String uniqueId;
    private final String isbn;
    private String title;
    private Author author;
    private String summary;
    private boolean isLent;
    private boolean isDeleted;

    public Book(String isbn, String title, Author author, String summary) {
        this.uniqueId = UUID.randomUUID().toString();
        this.isbn = Objects.requireNonNull(isbn);
        this.title = setTitle(title);
        this.author = author;
        this.summary = setSummary(summary);
        this.isDeleted = false;
        this.isLent = false;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public String setTitle(String title) {
        return this.title = INPUT_VALIDATOR.validateNoEmptyInput(title);
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String setSummary(String summary) {
        return this.summary = INPUT_VALIDATOR.replaceEmptyInput(summary);
    }

    public void lent() {
        if (isLent || isDeleted) {
            throw new NoBookAvailableForNowException(this.getTitle());
        }
        isLent = true;
    }

    public boolean isbnMatch(String isbnRegex) {
        Pattern pattern = Pattern.compile(isbnRegex.toLowerCase());
        return pattern.matcher(isbn.toLowerCase()).matches();
    }

    public boolean titleMatch(String titleRegex) {
        Pattern pattern = Pattern.compile(titleRegex.toLowerCase());
        return pattern.matcher(title.toLowerCase()).matches();
    }

    public void softDelete() {
        this.isDeleted = true;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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

    @Override
    public String toString() {
        return "Book{" +
                "uniqueLendingId='" + uniqueId + '\'' +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", summary='" + summary + '\'' +
                ", isLent=" + isLent +
                '}';
    }

    public void restore() {
        this.isDeleted = false;
    }
}
