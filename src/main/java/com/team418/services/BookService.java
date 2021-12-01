package com.team418.services;

import com.team418.domain.Book;
import com.team418.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book){
        return bookRepository.saveBook(book);
    }

    public Book getBook(String id){
        return bookRepository.getBook(id);
    }

    public Map<String, Book> getBooks() {
        return bookRepository.getBooks();
    }

    public List<Book> getBooksByIsbn(String regex) {
        List<Book> books = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);

        for (Book book : bookRepository.getBooks().values()) {
            Matcher matcher = pattern.matcher(book.getIsbn());
            if (matcher.matches()) {
                books.add(book);
            }
        }

        return books;
    }
//    public List<Book> getBooksByIsbn(String regex) {
//        List<Book> books = new ArrayList<>();
//
//        for (Book book : bookRepository.getBooks().values()) {
//            if (book.getIsbn().contains(regex)) {
//                books.add(book);
//            }
//        }
//
//        return books;
//    }
}
