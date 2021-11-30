package com.team418.api.book;

import com.team418.api.book.dto.BookDto;
import com.team418.api.book.dto.CreateBookDto;
import com.team418.domain.Author;
import com.team418.domain.Book;

public class BookMapper {
    // todo question: should this not be a singleton instead of a bunch of static methods?

    /**
     * @param book The book object to copy into a new book data transfer object
     * @return the new book DTO based on the fields of the provided book
     */
    public static BookDto bookToDto(Book book) {
        return new BookDto()
                .withIsbn(book.getIsbn())
                .withTitle(book.getTitle())
                .withAuthor(book.getAuthor())
                .withSummary(book.getSummary());
    }

    /**
     * The new book requires a valid ISBN, title, and author
     * The author requires at least a valid last name
     * If no summary or author first name are provided; an empty string comes in its place
     * @param createBookDto Creates a book out of a data transfer object
     * @return the book
     */
    public static Book createDtoToBook(CreateBookDto createBookDto) {
        Author author = new Author(replaceEmptyInput(createBookDto.getAuthor().getFirstName()), validateNoEmptyInput(createBookDto.getAuthor().getLastName()));

        return new Book(validateNoEmptyInput(createBookDto.getIsbn())
                , validateNoEmptyInput(createBookDto.getTitle())
                , author
                , replaceEmptyInput(createBookDto.getSummary()));
    }

    /**
     * @param input The fields to validate
     * @return the input if valid, throws invalid argument exception if it isn't
     */
    public static String validateNoEmptyInput(String input) throws IllegalArgumentException {
        if (input == null || input.length() < 1) throw new IllegalArgumentException(); // todo catch to 403; and provide a nice sentence -> in controller?
        return input;
    }


    /**
     * @param input A field to validate that's permitted to be empty
     * @return the non-empty field, or an empty string
     */
    public static String replaceEmptyInput(String input) {
        return (input == null) ? "" : input;
    }
}
