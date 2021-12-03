package com.team418.api.book;

import com.team418.api.book.dto.BookDto;
import com.team418.api.book.dto.CreateBookDto;
import com.team418.domain.Author;
import com.team418.domain.Book;

import static com.team418.services.inputvalidator.InputValidator.INPUT_VALIDATOR;

public class BookMapper {
    // todo question: should this not be a singleton instead of a bunch of static methods?

    /**
     * @param book The book object to copy into a new book data transfer object
     * @return the new book DTO based on the fields of the provided book
     */
    public static BookDto bookToDto(Book book) {
        return new BookDto(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getSummary());
    }

    /**
     * The new book requires a valid ISBN, title, and author
     * The author requires at least a valid last name
     * If no summary or author first name are provided; an empty string comes in its place
     *
     * @param createBookDto Creates a book out of a data transfer object
     * @return the book
     */
    public static Book createDtoToBook(CreateBookDto createBookDto) {
        Author author = new Author(INPUT_VALIDATOR.replaceEmptyInput(createBookDto.getAuthor().getFirstName()), INPUT_VALIDATOR.validateNoEmptyInput(createBookDto.getAuthor().getLastName()));

        return new Book(INPUT_VALIDATOR.validateNoEmptyInput(createBookDto.getIsbn())
                , INPUT_VALIDATOR.validateNoEmptyInput(createBookDto.getTitle())
                , author
                , INPUT_VALIDATOR.replaceEmptyInput(createBookDto.getSummary()));
    }

}
