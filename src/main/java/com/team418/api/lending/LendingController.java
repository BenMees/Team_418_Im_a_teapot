package com.team418.api.lending;

import com.team418.api.book.BookController;
import com.team418.api.lending.dto.CreateLendingDto;
import com.team418.api.lending.dto.LendingDto;
import com.team418.domain.Book;
import com.team418.domain.lending.Lending;
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

import static com.team418.domain.Feature.REGISTER_NEW_LENDING;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/lendings")
public class LendingController {

    private final static Logger TEST_LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final SecurityService securityService;
    private final MemberService memberService;
    private final LendingRepository lendingRepository;

    public LendingController(BookService bookService, SecurityService securityService, MemberService memberService, LendingRepository lendingRepository) {
        this.bookService = bookService;
        this.securityService = securityService;
        this.memberService = memberService;
        this.lendingRepository = lendingRepository;
        TEST_LOGGER.info("BookController Creation");
    }


    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public LendingDto registerLending(@RequestBody CreateLendingDto createLendingDto, @RequestHeader String authorization) {
        securityService.validate(authorization, REGISTER_NEW_LENDING);
        Book lendingBook = getLendableBook(bookService.getBooksByIsbn(createLendingDto.bookIsbn()), createLendingDto.bookIsbn());
        checkIfMemberExistsWithGivenInss(createLendingDto);
        Lending actualLending = LendingMapper.createLendingDtoToLending(createLendingDto);
        lendingRepository.addLending(actualLending);
        lendingBook.Lent();
        return LendingMapper.lendingToLendingDto(actualLending);
    }

    private void checkIfMemberExistsWithGivenInss(CreateLendingDto createLendingDto) {
        Member lendingMember = memberService.getMemberByInss(createLendingDto.memberInss());
        if (lendingMember == null) {
            throw new MemberNotFoundByInssException(createLendingDto.memberInss());
        }
    }

    private Book getLendableBook(List<Book> books, String isbn) {
        if (books.isEmpty()) {
            throw new NoBookFoundWithIsbnException(isbn);
        }
        if (books.size() > 1) {  // Multiple copies are defined in the Book so this should not be possible.
            throw new MoreThanOneIsbnMatchException(isbn, books.size());
        }
        return books.get(0);
    }
}
