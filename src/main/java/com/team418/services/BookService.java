package com.team418.services;

import com.team418.domain.Author;
import com.team418.domain.Book;
import com.team418.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
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


    public Book getBookByIsbn(String isbn) {
        return bookRepository.getBookByIsbn(isbn);
    }

    public List<Book> searchBooksCorrespondingIsbnPattern(String isbnQuery) {
        String isbnRegex = convertWildcardToRegex(isbnQuery);
        List<String> isbnMatches = bookRepository.getAllIsbnCorresponding(isbnRegex);
        return isbnMatches.stream()
                .map(bookRepository::getBookByIsbn)
                .collect(Collectors.toList());
    }

    public List<Book> searchBooksCorrespondingAuthorPattern(String partNamesQuery) {
        String partNamesRegex = convertWildcardToRegex(partNamesQuery);
        Set<Author> authorsCorresponding = bookRepository.getAllAuthorsCorresponding(partNamesRegex);
        return authorsCorresponding.stream()
                .flatMap(author -> (bookRepository.getBooksByAuthor(author)).stream())
                .collect(Collectors.toList());
    }

    public List<Book> searchBooksCorrespondingTitlePattern(String titleQuery) {
        String titleRegex = convertWildcardToRegex(titleQuery);
        Set<String> titlesCorresponding = bookRepository.getAllTitleCorresponding(titleRegex);
        return titlesCorresponding.stream()
                .flatMap(title -> (bookRepository.getBooksByTitle(title)).stream())
                .collect(Collectors.toList());
    }

    private String convertWildcardToRegex(String stringWithWildcard) {
        return stringWithWildcard.replaceAll("\\*", ".*");
    }
}
