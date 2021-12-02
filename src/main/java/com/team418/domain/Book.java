package com.team418.domain;

import java.util.Objects;
import java.util.UUID;

import static com.team418.services.inputvalidator.InputValidator.INPUT_VALIDATOR;


public class Book {
    private final String uniqueId;
    private final String isbn;
    private String title;
    private Author author;
    private String summary;
    private boolean isLent = false;
    private boolean isDeleted;

    public Book(String isbn, String title, Author author, String summary) {
        this.uniqueId = UUID.randomUUID().toString();
        this.isbn = isbn;
        this.title = setTitle(title);
        this.author = author;
        this.summary = setSummary(summary);
        this.isDeleted = false;
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

    public boolean Lent() {
        // todo remark: shouldn't this whole method be replaced by " return isLent = !isLent; " ? & why not void
       if (!isLent) {
           isLent = true;
           return true;
       }
       return false;
    }


    public void softDelete(){
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
                "uniqueId='" + uniqueId + '\'' +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", summary='" + summary + '\'' +
                ", isLent=" + isLent +
                '}';
    }
}
