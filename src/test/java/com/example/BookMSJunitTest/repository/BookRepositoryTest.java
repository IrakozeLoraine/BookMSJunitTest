package com.example.BookMSJunitTest.repository;

import com.example.BookMSJunitTest.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    private IBookRepository bookRepository;

    @Test
    public void findAll_success () {
        List<Book> books = bookRepository.findAll();
        assertEquals(2, books.size());
    }

    @Test
    public void findOne_success() {
        Optional<Book> book = bookRepository.findById(001);
        assertTrue(book.isPresent());
    }

    @Test
    public void findOne_fail() {
        Optional<Book> book = bookRepository.findById(003);
        assertEquals(false, book.isPresent());
    }

    @Test
    public void save_success(){
        Book newBook = new Book(3,"The Soul of a Butterfly, Second Edition","Muhammad Ali",2005,"Simon & Schuster",42500.0);
        bookRepository.save(newBook);

        Optional<Book> foundBook = bookRepository.findById(newBook.getId());
        assertTrue(foundBook.isPresent());
    }

    @Test
    public void remove_success(){
        Optional<Book> book = bookRepository.findById(002);
        if (book.isPresent()) bookRepository.deleteById(002);

        assertEquals(true, book.isPresent());
    }

    @Test
    public void remove_fail(){
        Optional<Book> book = bookRepository.findById(003);
        if (book.isPresent()) bookRepository.deleteById(003);

        assertEquals(false, book.isPresent());
    }

    @Test
    public void findByPublisher_success() {
        List<Book> book = bookRepository.findByPublisher("Warner Business Books");
        assertEquals(1, book.size());
    }

    @Test
    public void findByPublisher_fail() {
        List<Book> book = bookRepository.findByPublisher("XYZ Publisher");
        assertEquals(0, book.size());
    }

    @Test
    public void findByAuthor_success () {
        List<Book> books = bookRepository.findByAuthor("Muhammad Ali");
        assertEquals(1, books.size());
    }

    @Test
    public void findByAuthor_fail () {
        List<Book> books = bookRepository.findByAuthor("Loraine Irakoze");
        assertEquals(0, books.size());
    }

    @Test
    public void findByYear_success () {
        Optional<Book> book = bookRepository.findByYear(1997);
        assertEquals(001, book.get().getId());
    }

    @Test
    public void findByYear_fail () {
        Optional<Book> book = bookRepository.findByYear(2004);
        assertEquals(false, book.isPresent());
    }
}
