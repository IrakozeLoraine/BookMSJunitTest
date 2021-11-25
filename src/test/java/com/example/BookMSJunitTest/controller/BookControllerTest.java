package com.example.BookMSJunitTest.controller;

import com.example.BookMSJunitTest.model.Book;
import com.example.BookMSJunitTest.service.BookService;
import com.example.BookMSJunitTest.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @MockBean
    private BookService bookServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAll_success() throws Exception {
        List<Book> asList = Arrays.asList(new Book(001,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0),
                new Book(002,"The Soul of a Butterfly","Muhammad Ali",2003,"Simon & Schuster",42500.0));
        when(bookServiceMock.getAll()).thenReturn(asList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"id\":1,\"title\":\"Rich dad poor dad\",\"author\":\"Robert Kiyosaki\",\"year\":1997,\"publisher\":\"Warner Business Books\",\"cost\":47000.0}," +
                                "{\"id\":2,\"title\":\"The Soul of a Butterfly\",\"author\":\"Muhammad Ali\",\"year\":2003,\"publisher\":\"Simon & Schuster\",\"cost\":42500.0}]"))
                .andReturn();

    }

    @Test
    public void getByOne_success() throws Exception {
        Book book = new Book(2,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0);

        when(bookServiceMock.getById(book.getId())).thenReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/2")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":2,\"title\":\"Rich dad poor dad\",\"author\":\"Robert Kiyosaki\",\"year\":1997,\"publisher\":\"Warner Business Books\",\"cost\":47000.0}"))
                .andReturn();

    }

    @Test
    public void getByOne_404() throws Exception {
        Book book = new Book(2,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0);

        when(bookServiceMock.getById(book.getId())).thenReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/3")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"Book not found\"}"))
                .andReturn();

    }

    @Test
    public void getByAuthor_success() throws Exception {
        List<Book> book = Arrays.asList(new Book(001,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0),
        new Book(002, "The Greatest: My Own Story", "Muhammad Ali", 1975, "Random House", 59500.0));

        when(bookServiceMock.findByAuthor("Muhammad Ali")).thenReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/by-author/Muhammad Ali")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":001,\"title\":\"Rich dad poor dad\",\"author\":\"Robert Kiyosaki\",\"year\":1997,\"publisher\":\"Warner Business Books\",\"cost\":47000.0}," +
                        "{\"id\":002,\"title\":\"The Greatest: My Own Story\",\"author\":\"Muhammad Ali\",\"year\":1975,\"publisher\":\"Random House\",\"cost\":59500.0}]"))
                .andReturn();

    }

    @Test
    public void getByAuthor_fail() throws Exception {
        List<Book> book = Arrays.asList(new Book(002,"The Soul of a Butterfly","Muhammad Ali",2003,"Simon & Schuster",42500.0),
                new Book(003, "The Greatest: My Own Story", "Muhammad Ali", 1975, "Random House", 59500.0));

        when(bookServiceMock.findByAuthor("Muhammad Ali")).thenReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/by-author/Robert Kiyosaki")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"Books not found\"}"))
                .andReturn();

    }

    @Test
    public void getByPublisher_success() throws Exception {
        List<Book> book = Arrays.asList(new Book(001,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0),
                new Book(002, "How to Master the Art of Selling", "Tom Hopkins", 1979, "Warner Business Books", 59500.0));

        when(bookServiceMock.findByPublisher("Warner Business Books")).thenReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/by-publisher/Warner Business Books")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":001,\"title\":\"Rich dad poor dad\",\"author\":\"Robert Kiyosaki\",\"year\":1997,\"publisher\":\"Warner Business Books\",\"cost\":47000.0}," +
                        "{\"id\":002,\"title\":\"How to Master the Art of Selling\",\"author\":\"Tom Hopkins\",\"year\":1979,\"publisher\":\"Warner Business Books\",\"cost\":59500.0}]"))
                .andReturn();

    }

    @Test
    public void getByPublisher_fail() throws Exception {
        List<Book> book = Arrays.asList(new Book(001,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0),
                new Book(002, "How to Master the Art of Selling", "Tom Hopkins", 1979, "Warner Business Books", 59500.0));

        when(bookServiceMock.findByPublisher("Warner Business Books")).thenReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/by-publisher/Random House")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"Books not found\"}"))
                .andReturn();

    }

    @Test
    public void getByYear_success() throws Exception {
        Book book = new Book(2,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0);

        when(bookServiceMock.findByYear(1997)).thenReturn(java.util.Optional.of(book));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/by-year/1997")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":2,\"title\":\"Rich dad poor dad\",\"author\":\"Robert Kiyosaki\",\"year\":1997,\"publisher\":\"Warner Business Books\",\"cost\":47000.0}"))
                .andReturn();

    }

    @Test
    public void getByYear_fail() throws Exception {
        Book book = new Book(2,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0);

        when(bookServiceMock.findByYear(1997)).thenReturn(java.util.Optional.of(book));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/by-year/2005")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"Book not found\"}"))
                .andReturn();

    }

    @Test
    public void create_success() throws Exception{
        Book book = new Book(1,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0);
        when(bookServiceMock.createBook("Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0, 1)).thenReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(book));

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void update_success() throws Exception{
        when(bookServiceMock.updateBook(anyInt(), any(Book.class))).thenReturn(new Book(2,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0) ,
                new Book("Rich dad poor dad, Second Edition","Robert Kiyosaki",2005, "Warner Business Books", 47000.0));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Rich dad poor dad, Second Edition\",\"author\":\"Robert Kiyosaki\",\"year\":2005,\"publisher\":\"Warner Business Books\",\"cost\":47000.0}");
        mockMvc.perform(request).andExpect(status().isAccepted()).andExpect(content().json("{\"id\":2,\"title\":\"Rich dad poor dad\",\"author\":\"Robert Kiyosaki\",\"year\":1997,\"publisher\":\"Warner Business Books\",\"cost\":47000.0}"));

    }

    @Test
    public void delete_one_success() throws Exception{
        Book book = new Book(2,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0);
        doNothing().when(bookServiceMock).deleteBook(book.getId());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isAccepted());
    }

    @Test
    public void delete_one_fail() throws Exception{
        Book book = new Book(2,"Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0);

        doNothing().when(bookServiceMock).deleteBook(book.getId());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isAccepted());
    }
}
