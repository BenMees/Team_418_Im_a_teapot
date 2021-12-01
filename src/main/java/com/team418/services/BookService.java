package com.team418.services;

import com.team418.domain.Book;
import com.team418.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
        return bookRepository.saveBook(book);
    }

    public Book getBook(String id) {
        return bookRepository.getBook(id);
    }

    public List<Book> getBooks() {
        return bookRepository.getBooks();
    }


    public Book getBookByIsbn(String isbn){
        return bookRepository.getBookByIsbn(isbn);
    }

    public List<Book> searchBooksCorrespondingIsbnPattern(String isbnRegex){
        isbnRegex = isbnRegex.replaceAll("\\*", ".*");
        List<String> isbnMatches = bookRepository.getAllIsbnCorresponding(isbnRegex);
        return isbnMatches.stream()
                .map(bookRepository::getBookByIsbn)
                .collect(Collectors.toList());
    }
}
