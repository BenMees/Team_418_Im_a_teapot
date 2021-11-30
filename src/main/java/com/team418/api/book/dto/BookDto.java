package com.team418.api.book.dto;

import com.team418.domain.Author;

public class BookDto {
    private String isbn;
    private String title;
    private Author author;
    private String summary;

    public String getIsbn() {
        return isbn;
    }

    public BookDto withIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookDto withTitle(String title) {
        this.title = title;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public BookDto withAuthor(Author author) {
        this.author = author;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public BookDto withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", summary='" + summary + '\'' +
                '}';
    }
}
