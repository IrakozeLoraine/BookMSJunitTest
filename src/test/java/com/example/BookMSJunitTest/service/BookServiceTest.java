package com.example.BookMSJunitTest.service;

import com.example.BookMSJunitTest.model.Book;
import com.example.BookMSJunitTest.repository.IBookRepository;
import com.example.BookMSJunitTest.utils.Exceptions.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @Mock
    private IBookRepository bookRepositoryMock;

    @InjectMocks
    private BookService bookService;

    @Test
    public void getAll_success() {

        when(bookRepositoryMock.findAll()).thenReturn(Arrays.asList(new Book(1,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0),
                new Book(2,"The Soul of a Butterfly","Muhammad Ali",2003,"Simon & Schuster",42500.0)));
        assertEquals(1997,bookService.getAll().get(0).getYear());
    }

    @Test
    public  void create_success(){
        when(bookRepositoryMock.save(any(Book.class))).thenReturn(new Book(1,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0));
        assertEquals(1997,bookService.createBook("Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0, 1).getYear());
    }

    @Test
    public void delete_success(){
        Book bk = new Book(2,"The Soul of a Butterfly","Muhammad Ali",2003,"Simon & Schuster",42500.0);
        when(bookRepositoryMock.findById(bk.getId())).thenReturn(Optional.of(bk));

        bookService.deleteBook(bk.getId());

        verify(bookRepositoryMock).deleteById(bk.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void delete_fail(){
        doThrow(new ResourceNotFoundException("book", "id", 001)).when(bookRepositoryMock).findById(001);
        Book bk = new Book("The Soul of a Butterfly","Muhammad Ali",2003,"Simon & Schuster",42500.0);
        bookService.deleteBook(bk.getId());

        verify(bookRepositoryMock).deleteById(bk.getId());
    }

    @Test
    public void update_success(){
        Book bk = new Book(1,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0);
        Book newBk = new Book("The Soul of a Butterfly","Muhammad Ali",2003,"Simon & Schuster",42500.0);

        given(bookRepositoryMock.findById(bk.getId())).willReturn(Optional.of(bk));

        bookService.updateBook(bk.getId(),newBk);
        verify(bookRepositoryMock).save(newBk);
        verify(bookRepositoryMock).findById(bk.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void update_fail(){
        doThrow(new ResourceNotFoundException("book", "id", 001)).when(bookRepositoryMock).findById(001);
        Book bk = new Book("The Soul of a Butterfly","Muhammad Ali",2003,"Simon & Schuster",42500.0);
        bookService.updateBook(001,bk);
        verify(bookRepositoryMock).save(bk);
    }

    @Test
    public void getOne_success(){
        Book book = new Book();
        book.setId(2);

        when(bookRepositoryMock.findById(book.getId())).thenReturn(Optional.of(book));

        Book expected = bookService.getById(book.getId());

        assertThat(expected).isSameAs(book);

        verify(bookRepositoryMock).findById(book.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getOne_fail(){
        doThrow(new ResourceNotFoundException("book", "id", 001)).when(bookRepositoryMock).getById(001);
        bookService.getById(004);
    }

    @Test
    public void findByAuthor_success() {
        when(bookRepositoryMock.findByAuthor(anyString())).thenReturn(Arrays.asList(new Book(2,"The Soul of a Butterfly","Muhammad Ali",2003,"Simon & Schuster",42500.0), new Book(3, "The Greatest: My Own Story", "Muhammad Ali",1975,"Random House",59500.0)));

        assertEquals("The Soul of a Butterfly", bookService.findByAuthor("Muhammed Ali").get(0).getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findByAuthor_fail(){
        doThrow(new ResourceNotFoundException("books", "author", "Muhammed Ali")).when(bookRepositoryMock).findByAuthor("Muhammed Ali");
        bookService.findByAuthor("Muhammed Ali");
    }

    @Test
    public void findByPublisher_success() {
        List<Book> books = Arrays.asList(new Book(2,"The Soul of a Butterfly","Muhammad Ali",2003,"Random House",42500.0), new Book(3, "The Greatest: My Own Story", "Muhammad Ali",1975,"Random House",59500.0));
        when(bookRepositoryMock.findByPublisher(anyString())).thenReturn(books);

        assertEquals("The Soul of a Butterfly", bookService.findByPublisher("Random House").get(0).getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findByPublisher_fail(){
        doThrow(new ResourceNotFoundException("books", "publisher", "Random House")).when(bookRepositoryMock).findByPublisher("Random House");
        bookService.findByPublisher("Random House");
    }

    @Test
    public void findByYear_success() {
        when(bookRepositoryMock.findByYear(anyInt())).thenReturn(Optional.of(new Book(3, "The Greatest: My Own Story", "Muhammad Ali", 1975, "Random House", 59500.0)));

        assertEquals("The Greatest: My Own Story", bookService.findByYear(1975).get().getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findByYear_fail(){
        doThrow(new ResourceNotFoundException("books", "year", 1997)).when(bookRepositoryMock).findByYear(1997);
        bookService.findByYear(2003);
    }
}
