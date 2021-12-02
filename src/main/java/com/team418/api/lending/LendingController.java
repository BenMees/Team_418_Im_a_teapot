package com.team418.api.lending;

import com.team418.api.book.BookMapper;
import com.team418.api.book.dto.BookDto;
import com.team418.api.lending.dto.CreateLendingDto;
import com.team418.api.lending.dto.LendingDto;
import com.team418.domain.Book;
import com.team418.domain.Feature;
import com.team418.domain.lending.Lending;
import com.team418.domain.user.User;
import com.team418.exception.NoBookFoundWithIsbnException;
import com.team418.services.BookService;
import com.team418.services.LendingService;
import com.team418.services.security.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.stream.Collectors;

import static com.team418.domain.Feature.REGISTER_NEW_LENDING;
import static com.team418.domain.Feature.VIEW_OVERDUE_LENDINGS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/lendings")
public class LendingController {

    private final BookService bookService;
    private final SecurityService securityService;
    private final LendingService lendingService;

    public LendingController(BookService bookService, SecurityService securityService, LendingService lendingService) {
        this.bookService = bookService;
        this.securityService = securityService;
        this.lendingService = lendingService;
    }

    @GetMapping(params = "userId")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAllBooksBorrowedByMember(@RequestParam String userId, @RequestHeader String authorization) {
        securityService.validate(authorization, Feature.VIEW_BORROWED_BOOK);

        return lendingService.getAllBooksBorrowedByMember(userId).stream()
                .map(BookMapper::bookToDto)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public LendingDto registerLending(@RequestBody CreateLendingDto createLendingDto, @RequestHeader String authorization) {
        User lendingMember = securityService.validate(authorization, REGISTER_NEW_LENDING);

        Book lendingBook = checkIfBookIsNull(bookService.getBookByIsbn(createLendingDto.isbn()), createLendingDto.isbn());
        lendingBook.lent();

        Lending actualLending = LendingMapper.createLendingDtoToLending(createLendingDto, lendingMember.getUniqueId());
        lendingService.addLending(actualLending);

        return LendingMapper.lendingToLendingDto(actualLending);
    }

    //we know this is not restfull, we need to refactor this endpoint to param ?overdue
    @GetMapping(path = "/overdue", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<LendingDto> getOverdueLendings(@RequestHeader String authorization) {
        securityService.validate(authorization, VIEW_OVERDUE_LENDINGS);
        return lendingService.getOverdueLendings();
    }

    private Book checkIfBookIsNull(Book book, String isbn) {
        if (book == null) {
            throw new NoBookFoundWithIsbnException(isbn);
        }
        return book;
    }
}
