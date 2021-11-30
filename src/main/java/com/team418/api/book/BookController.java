package com.team418.api.book;

import com.team418.api.book.dto.BookDto;
import com.team418.api.book.dto.CreateBookDto;
import com.team418.domain.Book;
import com.team418.domain.user.User;
import com.team418.exception.UnauthorizedException;
import com.team418.exception.UnknownUserException;
import com.team418.repository.UserRepository;
import com.team418.services.BookService;
import com.team418.services.UserService;
import com.team418.services.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.team418.api.book.BookMapper.bookToDto;
import static com.team418.domain.Feature.REGISTER_NEW_BOOK;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "books")
public class BookController {
    private final static Logger TEST_LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final UserService userService;
    private final SecurityService securityService;

    public BookController(BookService bookService, UserService userService, SecurityService securityService) {
        this.bookService = bookService;
        this.userService = userService;
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

        // todo question: should securityService be a singleton > or will it always be the same because of dependency injection?
        // todo question: should the repositories be a singleton? (So we always get and use the same maps)^
        securityService.validateAccessToFeature(authorization, REGISTER_NEW_BOOK); // throws error if no access
        // validateAccess to feature should be 2 methods? Login vs actual access? (One method one purpose)

        Book book = BookMapper.createDtoToBook(createBookDto); // throws error

        Book savedBook = bookService.saveBook(book);
        return bookToDto(savedBook);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected void unauthorizedHandler(UnauthorizedException exception, HttpServletResponse response) throws IOException {
        response.sendError(UNAUTHORIZED.value(), exception.getMessage());
    }

    @ExceptionHandler(UnknownUserException.class)
    protected void unknownUserHandler(UnknownUserException exception, HttpServletResponse response) throws IOException {
        response.sendError(400, exception.getMessage()); // todo change number? #NotOurJob #NotOutStory xd
    }


    @ExceptionHandler(IllegalArgumentException.class)
    protected void illegalArgumentHandler(IllegalArgumentException exception, HttpServletResponse response) throws IOException {
        response.sendError(BAD_REQUEST.value(), exception.getMessage());
    }
}
