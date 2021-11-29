package com.team418.api.book;

import com.team418.api.book.dto.BookDto;
import com.team418.api.book.dto.CreateBookDto;
import com.team418.domain.Book;
import com.team418.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.team418.api.book.BookMapper.bookToDto;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "books")
public class BookController {
    private final static Logger TEST_LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
        TEST_LOGGER.info("BookController Creation");
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBook(@PathVariable String id) {
        return bookToDto(bookService.getBook(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAll(){
       bookService.getBooks().values().forEach(n->TEST_LOGGER.info(n.getUniqueId()));
        return bookService.getBooks().values().stream()
                .map(BookMapper::bookToDto)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto registerNewBook(@RequestBody CreateBookDto createBookDto) {
        Book book = BookMapper.createDtoToBook(createBookDto);
        Book savedBook = bookService.saveBook(book);
        return bookToDto(savedBook);

        // todo check this method (test?)
        // todo if any other user besides a librarian tries to register a new book,
        //  let the server respond with 403 Forbidden and a custom message.


    }

}
