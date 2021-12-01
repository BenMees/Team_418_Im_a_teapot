package com.team418.api.book;

import com.team418.api.book.BookMapper;
import com.team418.api.book.dto.BookDto;
import com.team418.api.book.dto.CreateBookDto;
import com.team418.domain.Author;
import com.team418.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.team418.api.book.BookMapper.bookToDto;
import static com.team418.services.inputvalidator.InputValidator.INPUT_VALIDATOR;

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

    @Test
    void givenAllowedEmptyInputString_whenValidating_thenReturnEmptyString() {
        String allowedEmptyString = "";
        Assertions.assertThat(INPUT_VALIDATOR.replaceEmptyInput(allowedEmptyString)).isEqualTo(allowedEmptyString);
    }

    @Test
    void givenAllowedNonEmptyInputString_whenValidating_thenReturnInput() {
        String allowedString = "Not empty";
        Assertions.assertThat(INPUT_VALIDATOR.replaceEmptyInput(allowedString)).isEqualTo(allowedString);
        Assertions.assertThat(INPUT_VALIDATOR.validateNoEmptyInput(allowedString)).isEqualTo(allowedString);
    }

    @Test
    void givenNonAllowedEmptyInputString_whenValidating_thenThrowError() {
        String invalidEmptyInput = "";

        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> INPUT_VALIDATOR.validateNoEmptyInput(invalidEmptyInput));
        // optional .hasMessageMatching("Some message")
    }

    @Test
    void givenAllValidStringInputs_whenCreatingNewBook_thenCreateBook() {
        Author validAuthor = new Author("valid", "input");
        String validIsbn = "123456";
        String validSummary = "Cool story bro";
        String validTitle = "My diary";

        CreateBookDto createBookDto = new CreateBookDto()
                .setAuthor(validAuthor)
                .setIsbn(validIsbn)
                .setSummary(validSummary)
                .setTitle(validTitle);


        Book bookToValidate = BookMapper.createDtoToBook(createBookDto);

        Assertions.assertThat(validAuthor).isEqualTo(bookToValidate.getAuthor());
        Assertions.assertThat(validIsbn).isEqualTo(bookToValidate.getIsbn());
        Assertions.assertThat(validSummary).isEqualTo(bookToValidate.getSummary());
        Assertions.assertThat(validTitle).isEqualTo(bookToValidate.getTitle());
    }

    @Test
    void givenSomeValidEmptyStringInputs_whenCreatingNewBook_thenCreateBook() {
        String validEmptyInput = "";
        Author validAuthor = new Author(validEmptyInput, "input");
        String validIsbn = "123456";
        String validTitle = "My diary";

        CreateBookDto createBookDto = new CreateBookDto()
                .setAuthor(validAuthor)
                .setIsbn(validIsbn)
                .setSummary(validEmptyInput)
                .setTitle(validTitle);


        Book bookToValidate = BookMapper.createDtoToBook(createBookDto);

        Assertions.assertThat(validAuthor).isEqualTo(bookToValidate.getAuthor());
        Assertions.assertThat(validIsbn).isEqualTo(bookToValidate.getIsbn());
        Assertions.assertThat(validEmptyInput).isEqualTo(bookToValidate.getSummary());
        Assertions.assertThat(validTitle).isEqualTo(bookToValidate.getTitle());
    }

    @Test
    void givenInvalidEmptyStringInputs_whenCreatingNewBook_thenThrowError() {
        String invalidEmptyInput = "";
        Author validAuthor = new Author("", "input");

        CreateBookDto createBookDto = new CreateBookDto()
                .setAuthor(validAuthor)
                .setIsbn(invalidEmptyInput)
                .setSummary("")
                .setTitle(invalidEmptyInput);


        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> BookMapper.createDtoToBook(createBookDto));
        // optional .hasMessageMatching("Some message")
    }
}
