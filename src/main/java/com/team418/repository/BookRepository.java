package com.team418.repository;

//import com.team418.domain.Author; // testing purposes

import com.team418.domain.Author;
import com.team418.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Repository
public class BookRepository {
    private final Map<String, Book> books;

    public BookRepository() {
        books = new ConcurrentHashMap<>();
    }

    public List<Book> getBooks() {
        return books.values().stream()
                .filter(book -> !book.isDeleted())
                .collect(Collectors.toList());
    }

    public Book saveBook(Book book) {
        books.put(book.getUniqueId(), book);
        return book;
    }

    public Book getBook(String uniqueId) {
        return books.get(uniqueId);
    }

    public List<Book> getBooksByAuthor(Author author) {
        return this.books.values().stream()
                .filter(book -> book.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    public Book getBookByIsbn(String isbn) {
        return this.books.values().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<Book> getBooksByTitle(String title) {
        return this.books.values().stream()
                .filter(book -> book.titleMatch(title))
                .collect(Collectors.toList());
    }

    public List<String> getAllIsbnCorresponding(String isbnRegex) {
        return this.books.values().stream()
                .filter(book -> book.isbnMatch(isbnRegex))
                .map(Book::getIsbn)
                .collect(Collectors.toList());
    }

    public Set<String> getAllTitleCorresponding(String titleRegex) {
        return this.books.values().stream()
                .filter(book -> book.titleMatch(titleRegex))
                .map(Book::getTitle)
                .collect(Collectors.toSet());
    }

    public Set<Author> getAllAuthorsCorresponding(String partNamesRegex) {
        return this.books.values().stream()
                .map(Book::getAuthor)
                .filter(author -> author.firstNameORLastNameMatch(partNamesRegex))
                .collect(Collectors.toSet());
    }
}
