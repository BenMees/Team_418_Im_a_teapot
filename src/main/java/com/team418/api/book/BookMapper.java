package com.team418.api.book;

import com.team418.api.book.dto.BookDto;
import com.team418.api.book.dto.CreateBookDto;
import com.team418.domain.Book;

public class BookMapper {

    public static BookDto bookToDto(Book book) {
        return new BookDto()
                .withIsbn(book.getIsbn())
                .withTitle(book.getTitle())
                .withAuthor(book.getAuthor())
                .withSummary(book.getSummary());
    }

    public static Book createDtoToBook(CreateBookDto createBookDto) {
        // todo if one of the non-required feels is empty, put in an empty string
        //  The ISBN, title and author's last name are required.
        return new Book(createBookDto.getIsbn(), createBookDto.getTitle(), createBookDto.getAuthor(), createBookDto.getSummary());
    }
}
