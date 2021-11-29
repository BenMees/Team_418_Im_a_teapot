package com.team418.api;

import com.team418.domain.Book;

public class BookMapper {

    public BookDto bookToDto(Book book) {
        return new BookDto()
                .withIsbn(book.getIsbn())
                .withTitle(book.getTitle())
                .withAuthor(book.getAuthor())
                .withSummary(book.getSummary());
    }
}
