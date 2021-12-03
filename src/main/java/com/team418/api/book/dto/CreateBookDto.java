package com.team418.api.book.dto;

import com.team418.domain.Author;

public record CreateBookDto(String isbn, String title, Author author,
                            String summary) {
}
