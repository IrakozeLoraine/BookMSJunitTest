package com.example.BookMSJunitTest.controller;

import com.example.BookMSJunitTest.model.Book;
import com.example.BookMSJunitTest.utils.APIResponse;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAll_success() throws JSONException {
        String response = this.restTemplate.getForObject("/", String.class);

        JSONAssert.assertEquals("[{id:001}, {id:002}]", response, false);
    }
    @Test
    public void getById_success() {
        ResponseEntity<Book> response = this.restTemplate.getForEntity("/2", Book.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2003, response.getBody().getYear());
        assertEquals("The Soul of a Butterfly", response.getBody().getTitle());
    }
    @Test
    public void getById_404() {
        ResponseEntity<APIResponse> response = this.restTemplate.getForEntity("/3", APIResponse.class);

        System.out.println(response);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Book not found", response.getBody().getMessage());
    }

    @Test
    public void create_success() {
        Book request = new Book("Rich dad poor dad","Robert Kiyosaki",1997, "Warner Business Books", 47000.0);
        ResponseEntity<Book> response = this.restTemplate.postForEntity("/", request, Book.class);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(1997, response.getBody().getYear());
        assertEquals("Rich dad poor dad", response.getBody().getTitle());
    }
}
