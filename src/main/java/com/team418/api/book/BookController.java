package com.team418.api.book;

import com.team418.api.book.dto.*;
import com.team418.domain.Book;
import com.team418.exception.CreateBookWithAlreadyExistingIsbnException;
import com.team418.services.BookService;
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
    private final SecurityService securityService;

    public BookController(BookService bookService, SecurityService securityService) {
        this.bookService = bookService;
        this.securityService = securityService;
        TEST_LOGGER.info("BookController Creation");
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBook(@PathVariable String id, @RequestHeader String authorization) {
        Book book = bookService.getBook(id);


        if (book.isDeleted()) {
            securityService.validate(authorization, VIEW_DELETED_BOOK);
        }
        return bookToDto(book);
    }

    @GetMapping(params = "isbnContains")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getBooksByIsbn(@RequestParam String isbnContains) {
        return bookService.searchBooksCorrespondingIsbnPattern(isbnContains)
                .stream()
                .map(BookMapper::bookToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(params = "authorsContains")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getBooksByAuthor(@RequestParam String authorsContains) {
        return null;
    }

    //waiting for implementing
    @GetMapping(params = "titleContains")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getBooksByTitle(@RequestParam String titleContains) {
        return null;
    }


    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAll() {
        bookService.getBooks().forEach(n -> TEST_LOGGER.info(n.getUniqueId()));
        return bookService.getBooks().stream()
                .map(BookMapper::bookToDto)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto registerNewBook(@RequestBody CreateBookDto createBookDto, @RequestHeader String authorization) {
        TEST_LOGGER.info("Register a new book");
        securityService.validate(authorization, REGISTER_NEW_BOOK);

        checkIfBookAlreadyExists(createBookDto);

        Book book = BookMapper.createDtoToBook(createBookDto); // throws error

        Book savedBook = bookService.saveBook(book);
        return bookToDto(savedBook);
    }

    private void checkIfBookAlreadyExists(CreateBookDto createBookDto) {
        if (bookService.getBookByIsbn(createBookDto.getIsbn()) != null) {
            throw new CreateBookWithAlreadyExistingIsbnException(createBookDto.getIsbn());
        }
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

    @PutMapping(path = "restore/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BookDto restoreBook(@PathVariable String id, @RequestBody UpdateBookDto updateBookDto, @RequestHeader String authorization) {
        TEST_LOGGER.info("restore a book");
        securityService.validate(authorization, RESTORE_BOOK);
        Book bookToRestore = bookService.getBook(id);
        restoreBook(updateBookDto, bookToRestore);
        return bookToDto(bookToRestore);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable String id, @RequestHeader String authorization) {
        TEST_LOGGER.info("delete a book");
        securityService.validate(authorization, DELETE_BOOK);
        Book bookToDelete = bookService.getBook(id);
        bookToDelete.softDelete();
    }

    private void updateBook(UpdateBookDto updateBookDto, Book bookToUpdate) {
        bookToUpdate.setTitle(updateBookDto.getTitle());
        bookToUpdate.setAuthor(updateBookDto.getAuthor());
        bookToUpdate.setSummary(updateBookDto.getSummary());
    }

    private void restoreBook(UpdateBookDto restoreBookDto, Book bookToRestore) {
        bookToRestore.setDeleted(restoreBookDto.isDeleted());
    }
}
