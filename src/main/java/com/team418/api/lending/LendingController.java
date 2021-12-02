package com.team418.api.lending;

import com.team418.api.lending.dto.CreateLendingDto;
import com.team418.api.lending.dto.LendingDto;
import com.team418.domain.Book;
import com.team418.domain.lending.Lending;
import com.team418.domain.user.User;
import com.team418.exception.NoBookFoundWithIsbnException;
import com.team418.exception.NoLendingFoundException;
import com.team418.exception.UnauthorizedException;
import com.team418.services.BookService;
import com.team418.services.LendingService;
import com.team418.services.security.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.team418.domain.Feature.REGISTER_NEW_LENDING;
import static com.team418.domain.Feature.RETURN_lENDED_BOOK;
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

    @DeleteMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String returnLentBook(@RequestBody String lending , @RequestHeader String authorization) {
        User lendingMember = securityService.validate(authorization, RETURN_lENDED_BOOK);
        Lending lendingOfMember = lendingService.getLendingById(lending);
        checkIfLendExist(lendingOfMember);
        checkMemberIsTheOneWhoLentTheBook(lendingMember, lendingOfMember);
        return lendingOfMember.returnBook();
    }

    private void checkMemberIsTheOneWhoLentTheBook(User lendingMember, Lending lendingOfMember) {
        if (!lendingOfMember.getMemberId().equals(lendingMember.getUniqueId())) {
            throw new UnauthorizedException("This Member did not lent the proposed book");
        }
    }

    private void checkIfLendExist(Lending membersLend) {
        if (membersLend == null) {
            throw new NoLendingFoundException();
        }
    }

    private Book checkIfBookIsNull(Book book, String isbn) {
        if (book == null) {
            throw new NoBookFoundWithIsbnException(isbn);
        }
        return book;
    }
}
