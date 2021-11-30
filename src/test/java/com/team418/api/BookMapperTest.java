package com.team418.api;

import com.team418.domain.Author;
import com.team418.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.team418.api.BookMapper.bookToDto;

public class BookMapperTest {
    private Book book;
    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("tom", "tom");
        book = new Book("123", "test", author, "short summary");
    }

    @Test
    void givenABook_whenMappingToBookDto_thenReturnBookDto() {
        BookDto dto = bookToDto(book);

        Assertions.assertThat(dto.getIsbn()).isEqualTo("123");
        Assertions.assertThat(dto.getTitle()).isEqualTo("test");
        Assertions.assertThat(dto.getAuthor()).isEqualTo(author);
        Assertions.assertThat(dto.getSummary()).isEqualTo("short summary");
    }
}