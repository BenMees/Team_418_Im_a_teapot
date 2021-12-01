package com.team418.api.book;

import com.team418.api.book.dto.*;
import com.team418.domain.Book;
import com.team418.domain.Lending;
import com.team418.domain.user.Member;
import com.team418.exception.MemberNotFoundByInssException;
import com.team418.exception.MoreThanOneIsbnMatchException;
import com.team418.exception.NoBookFoundWithIsbnException;
import com.team418.repository.LendingRepository;
import com.team418.services.BookService;
import com.team418.services.MemberService;
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
    private final MemberService memberService;
    private final LendingRepository lendingRepository;

    public BookController(BookService bookService, SecurityService securityService, MemberService memberService, LendingRepository lendingRepository) {
        this.bookService = bookService;
        this.securityService = securityService;
        this.memberService = memberService;
        this.lendingRepository = lendingRepository;
        TEST_LOGGER.info("BookController Creation");
    }

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBook(@PathVariable String id) {
        return bookToDto(bookService.getBook(id));
    }

    @GetMapping(params = "isbnContains")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getBooksByIsbn(@RequestParam String isbnContains) {
        List<Book> books = bookService.getBooksByIsbn(isbnContains);
        return null;
    }


    //waiting for implementing
    @GetMapping(params = "authorsContains")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getBooksByAuthor(@RequestParam String authorsContains){
        return null;
    }

    //waiting for implementing
    @GetMapping(params = "titleContains")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getBooksByTitle(@RequestParam String titleContains){
        return null;
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

    @PostMapping(path = "/lendings" ,consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public LendingDto registerLending(@RequestBody CreateLendingDto createLendingDto, @RequestHeader String authorization) {
        securityService.validate(authorization, REGISTER_NEW_LENDING);
        Book lendingBook = getLendableBook(bookService.getBooksByIsbn(createLendingDto.bookIsbn()),createLendingDto.bookIsbn());
        checkIfMemeberExcistWithGivenInss(createLendingDto);
        Lending actualLending = BookMapper.createLendingDtoToLending(createLendingDto);
        lendingRepository.addLending(actualLending);
        lendingBook.Lent();
        return BookMapper.lendingToLendingDto(actualLending);
    }

    private void checkIfMemeberExcistWithGivenInss(CreateLendingDto createLendingDto) {
        Member lendingMember = memberService.getMemberByInss(createLendingDto.memberInss());
        if (lendingMember == null) {
            throw new MemberNotFoundByInssException(createLendingDto.memberInss());
        }
    }

    private Book getLendableBook(List<Book> books,String isbn) {
        if (books.isEmpty()) {
            throw new NoBookFoundWithIsbnException(isbn);
        }
        if (books.size() > 1) {  // Multiple copies are defined in the Book so this should not be possible.
            throw new MoreThanOneIsbnMatchException(isbn, books.size());
        }
        return books.get(0);
    }

    private void updateBook(UpdateBookDto updateBookDto, Book bookToUpdate) {
        bookToUpdate.setTitle(updateBookDto.getTitle());
        bookToUpdate.setAuthor(updateBookDto.getAuthor());
        bookToUpdate.setSummary(updateBookDto.getSummary());
    }
}
