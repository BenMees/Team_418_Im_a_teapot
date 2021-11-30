package com.team418.api.book.dto;

import com.team418.domain.Author;

public class CreateBookDto {
    private String isbn;
    private String title;
    private Author author;
    private String summary;

    public CreateBookDto setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public CreateBookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public CreateBookDto setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public CreateBookDto setSummary(String summary) {
        this.summary = summary;
        return this;
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
}
