package com.example.BookMSJunitTest.controller;

import com.example.BookMSJunitTest.model.Book;
import com.example.BookMSJunitTest.repository.IBookRepository;
import com.example.BookMSJunitTest.service.BookService;
import com.example.BookMSJunitTest.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private IBookRepository bookRepository;

    @PostMapping("/")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(book.getTitle(), book.getAuthor(), book.getYear(), book.getPublisher(), book.getCost(), book.getId()));
    }

    @GetMapping("/")
    public List<Book> getAll() {

        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "Book not found"));
        }
        Book book = bookService.getById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/by-publisher/{publisher}")
    public ResponseEntity<?> findByPublisher(@PathVariable String publisher) {
        List<Book> books = bookService.findByPublisher(publisher);

        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "Books not found"));
        }

        return ResponseEntity.ok(books);
    }

    @GetMapping("/by-author/{author}")
    public ResponseEntity<?> findByAuthor(@PathVariable String author) {
        List<Book> books = bookService.findByAuthor(author);

        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "Books not found"));
        }


        return ResponseEntity.ok(books);
    }

    @GetMapping("/by-year/{year}")
    public ResponseEntity<?> findByYear(@PathVariable int year) {
        Optional<Book> book = bookService.findByYear(year);
        if (book.isPresent()) return ResponseEntity.ok(book);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "Book not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable int id) {

        bookService.deleteBook(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Removed");
    }
}