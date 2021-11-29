package com.team418.repository;
import com.team418.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookRepository {
    private final Map<String, Book> books;


    public BookRepository() {
        books = new ConcurrentHashMap<>();
//        Book book1 = new Book("1","lskdj",new Author("sdf","sdf"),"df");
//        Book book2 = new Book("2","lskdj",new Author("sdf","sdf"),"df");
//
//        this.saveBook(book1);
//        this.saveBook(book2);
    }

    public Map<String, Book> getBooks() {
        return books;
    }

    public Book saveBook(Book book) {
        return books.put(book.getUniqueId(), book);
    }

    public Book getBook(String uniqueId) {

        return books.get(uniqueId);
    }
}
