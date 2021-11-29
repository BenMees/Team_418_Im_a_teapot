package com.team418.api;

import com.team418.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "books")
public class BookController {
    private final static Logger TEST_LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookController() {
        TEST_LOGGER.info("BookController Creation");
        this.bookRepository = new BookRepository();
        this.bookMapper = new BookMapper();
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBook(@PathVariable String id){
        return null;
    }


}
