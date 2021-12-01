package com.example.BookMSJunitTest.service;

import com.example.BookMSJunitTest.model.Book;
import com.example.BookMSJunitTest.repository.IBookRepository;
import com.example.BookMSJunitTest.utils.Exceptions.ResourceNotFoundException;
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
        if (findById.isPresent()) {
            Book book = findById.get();
            return book;
        }
        throw new ResourceNotFoundException("Book", "id", id);
    }

    public Book createBook(String title, String author, int year, String publisher, Double cost, Integer id) {
        Book book = new Book(id, title, author, year, publisher, cost);

        return bookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id" + id));
        bookRepository.deleteById(id);
    }

    public Book updateBook(Integer id, Book book) {
        bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id" + id));
        book.setId(id);

        return bookRepository.save(book);
    }

    public List<Book> findByPublisher(String publisher) {
        List<Book> books = bookRepository.findByPublisher(publisher);

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("Books not found with publisher " + publisher);
        }
        return books;
    }

    public List<Book> findByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor(author);
        if (books.isEmpty()) {
            throw new ResourceNotFoundException("Books not found with author " + author);
        }
        return books;

    }

    public Optional<Book> findByYear(int year) {
        Optional<Book> book = bookRepository.findByYear(year);
        if (book.isPresent()) {
            return book;
        }
        throw new ResourceNotFoundException("Book not found with year" + year);

    }

}
