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

    public Map<String, Book> getBooks() {
        return books;
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
                .filter(book-> book.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    public Set<Author> getAllAuthorsCorresponding(String partNamesRegex){
        return this.books.values().stream()
                .map(Book::getAuthor)
                .filter(author -> author.firstNameORLastNameCorresponding(partNamesRegex))
                .collect(Collectors.toSet());
    }


    public Book getBookByIsbn(String isbn){
        return this.books.values().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<String> getAllIsbnCorresponding(String regex){
        Pattern pattern = Pattern.compile(regex);

        return this.books.values().stream()
                .map(Book::getIsbn)
                .filter(isbn -> pattern.matcher(isbn).matches())
                .collect(Collectors.toList());
    }
}
