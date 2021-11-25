package com.example.BookMSJunitTest.repository;

import com.example.BookMSJunitTest.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByAuthor(String author);

    List<Book> findByPublisher(String publisher);

    Optional<Book> findByYear(int year);
}