package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

//You defined the webEnvironment attribute to be RANDOM_PORT, which tells Spring to start up a fully running web server on a random port.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerFunctionalTest {
	
	@Autowired
	TestRestTemplate template;
	
	//test find-all
	@Test
	void shouldReturnAllBooks() {
		ResponseEntity<Book[]> entity = template.getForEntity("/books", Book[].class);
		
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, entity.getHeaders().getContentType());
		
		Book[] books = entity.getBody();
		// This will not work
		// ResponseEntity<List<Book>> entity = template.getForEntity("/books", List.class)
		assertTrue(books.length >=3);
		assertEquals("97 Things Every Java Programmer Should Know",books[0].getTitle());
        assertEquals("Spring Boot: Up and Running",books[1].getTitle());
        assertEquals("Hacking with Spring Boot 2.3: Reactive Edition",books[2].getTitle());
	}

	@Test
	void shouldReturnAValidBook() {
		Book book = template.getForObject("/books/1", Book.class);
		assertEquals(1, book.getId());
		assertEquals("97 Things Every Java Programmer Should Know", book.getTitle());
		assertEquals("Kevlin Henney, Trisha Gee", book.getAuthor());
		assertEquals("OReilly Media, Inc.", book.getPublisher());
		assertEquals("May 2020", book.getReleaseDate());
	}
	
	@Test
    void invalidBookIdShouldReturn404() {
        ResponseEntity<Book> entity = template.getForEntity("/books/99", Book.class);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }
	
	@Test
    void shouldCreateNewBook() {
        Book book = new Book(4,
                "Fundamentals of Software Architecture: An Engineering Approach",
                "Mark Richards, Neal Ford",
                "Upfront Books",
                "Feb 2021",
                "B08X8H15BW",
                "Architecture");
        ResponseEntity<Book> entity = template.postForEntity("/books", book, Book.class);
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        
        Book created = entity.getBody();
        assertEquals(4,created.getId());
        assertEquals("Fundamentals of Software Architecture: An Engineering Approach",created.getTitle());
        assertEquals("Mark Richards, Neal Ford",created.getAuthor());
        assertEquals("Upfront Books",created.getPublisher());
        assertEquals("Feb 2021", created.getReleaseDate());
        assertEquals("B08X8H15BW",created.getIsbn());
        assertEquals("Architecture",created.getTopic());
        
        //Finally, make another call to get all of the books to verify that there is now one more in the collection for a total of four:
        assertEquals(4, template.getForObject("/books", List.class).stream().count());
    }
}
