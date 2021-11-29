package com.team418.services;

import com.team418.domain.Book;
import com.team418.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void saveBook(Book book){
        bookRepository.saveBook(book);
    }

    public Book getBook(String id){
        return bookRepository.getBook(id);
    }

    public Map<String, Book> getBooks() {
        return bookRepository.getBooks();
    }
}
