package com.example.BookMSJunitTest.service;

import com.example.BookMSJunitTest.model.Book;
import com.example.BookMSJunitTest.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private IBookRepository bookRepository;

    public List<Book> getAll() {

        List<Book> books = bookRepository.findAll();

        return books;
    }

    public Book getById(int id) {
        Optional<Book> findById = bookRepository.findById(id);
        if(findById.isPresent()) {
            Book book = findById.get();
            return book;
        }
        return null;
    }
    public Book createBook(String title, String author, int year, String publisher, Double cost, Integer id){
        Book book = new Book(id, title, author, year, publisher, cost);

        return bookRepository.save(book);
    }

    public void deleteBook(Integer id){
        bookRepository.findById(id)
                .orElseThrow( ()->new RuntimeException("Book not found with id"+ id));
        bookRepository.deleteById(id);
    }

    public Book updateBook(Integer id, Book book){
        bookRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Book not found with id"+ id));
        book.setId(id);

        return bookRepository.save(book);
    }
    public List<Book> findByPublisher(String publisher){
        return bookRepository.findByPublisher(publisher);
    }

    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public Optional<Book> findByYear(int year) {
        return bookRepository.findByYear(year);
    }

}
