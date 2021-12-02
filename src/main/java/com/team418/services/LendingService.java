package com.team418.services;

import com.team418.domain.Book;
import com.team418.domain.lending.Lending;
import com.team418.repository.LendingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LendingService {
    private final LendingRepository lendingRepository;
    private final BookService bookService;

    public LendingService(LendingRepository lendingRepository, BookService bookService) {
        this.lendingRepository = lendingRepository;
        this.bookService = bookService;
    }

    public List<Book> getAllBooksBorrowedByMember(String userId){
        return lendingRepository.getLendings()
                .stream()
                .filter(lending -> !lending.isReturned() && lending.getMemberId().equals(userId))
                .map(lending -> bookService.getBookByIsbn(lending.getBookIsbn()))
                .collect(Collectors.toList());
    }


    public void addLending(Lending lending) {
        lendingRepository.addLending(lending);
    }
}
