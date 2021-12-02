package com.team418.api.book.dto;

import com.team418.domain.Author;

public class UpdateBookDto {
    private String title;
    private Author author;
    private String summary;
    private boolean isDeleted;

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

    public UpdateBookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public UpdateBookDto setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public UpdateBookDto setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public UpdateBookDto setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }
}
