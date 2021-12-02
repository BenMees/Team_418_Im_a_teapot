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
}
