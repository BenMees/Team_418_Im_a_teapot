package com.team418.api.book;

import com.team418.api.book.dto.BookDto;
import com.team418.api.book.dto.CreateBookDto;
import com.team418.api.book.dto.UpdateBookDto;
import com.team418.domain.Book;

import com.team418.services.BookService;

import com.team418.services.LibraryService;
import com.team418.services.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.team418.api.book.BookMapper.bookToDto;
import static com.team418.domain.Feature.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "books")
public class BookController {
    private final static Logger TEST_LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final LibraryService libraryService;
    private final SecurityService securityService;

    public BookController(BookService bookService, LibraryService libraryService, SecurityService securityService) {
        this.bookService = bookService;
        this.libraryService = libraryService;
        this.securityService = securityService;
        TEST_LOGGER.info("BookController Creation");
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBook(@PathVariable String id) {
        return bookToDto(bookService.getBook(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAll() {
        bookService.getBooks().values().forEach(n -> TEST_LOGGER.info(n.getUniqueId()));
        return bookService.getBooks().values().stream()
                .map(BookMapper::bookToDto)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto registerNewBook(@RequestBody CreateBookDto createBookDto, @RequestHeader String authorization) {
        TEST_LOGGER.info("Register a new book");
        securityService.validate(authorization, REGISTER_NEW_BOOK);
        Book book = BookMapper.createDtoToBook(createBookDto); // throws error

        Book savedBook = bookService.saveBook(book);
        return bookToDto(savedBook);
    }

    @PutMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BookDto updateBook(@PathVariable String id, @RequestBody UpdateBookDto updateBookDto, @RequestHeader String authorization) {
        TEST_LOGGER.info("update a book");
        securityService.validate(authorization, UPDATE_BOOK);
        Book bookToUpdate = bookService.getBook(id);
        updateBook(updateBookDto, bookToUpdate);
        return bookToDto(bookToUpdate);

    }

    private void updateBook(UpdateBookDto updateBookDto, Book bookToUpdate) {
        bookToUpdate.setTitle(updateBookDto.getTitle());
        bookToUpdate.setAuthor(updateBookDto.getAuthor());
        bookToUpdate.setSummary(updateBookDto.getSummary());
    }
}
