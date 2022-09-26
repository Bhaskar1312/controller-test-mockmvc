package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
//Using this annotation will disable full autoconfiguration 
//and instead apply only configuration relevant to MVC tests
//(i.e., @Controller, @ControllerAdvice, @JsonComponent, Converter/GenericConverter,
//Filter, WebMvcConfigurer, and HandlerMethodArgumentResolver beans 
//but not @Component, @Service, or @Repository beans).

//you are telling Spring that the only bean that should be used in this test is the BookController class.
//This might not make a big difference in our small sample application, but as your apps grow and you have
//hundreds or thousands of beans managed by Spring, you don't want them all being loaded into the ApplicationContext just to run this single test.

//By default, tests annotated with @WebMvcTest will also autoconfigure Spring Security and MockMVC. This means that just 
//by using this annotation, you will have access to an instance of MockMVC.
public class BookControllerTest {
	
	//added the @WebMvcTest annotation to the BookControllerTest class. With that, Spring will autoconfigure MockMVC for you
	 @Autowired
	 MockMvc mvc;
	 
	 //We can use Mockito's @MockBean annotation to add mocks to a Spring application context. Then, in our individual tests, you can mock the behavior of the BookService methods:
	// mock-mvc
    @MockBean
    BookService bookService;
    
    private List<Book> getBooks() {
		Book one = new Book(1, "97 Things Every Java Programmer Should Know", "Kevlin Henney, Trisha Gee",
				"OReilly Media, Inc.", "May 2020", "9781491952696", "Java");
		Book two = new Book(2, "Spring Boot: Up and Running", "Mark Heckler", "OReilly Media, Inc.", "February 2021",
				"9781492076919", "Spring");
		return List.of(one, two);
    }
    
    @Test
    void findAllShouldReturnAllBooks() throws Exception {
        Mockito.when(this.bookService.findAll()).thenReturn(getBooks());

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        //The Mockito.when() method is mocking the behavior of the BookService's findAll() method. When that method is encountered in the controller, it will use your mock behavior to return the list of books.
        //The perform method comes from MockMvc and will perform a request and return a type that allows chaining further actions, such as asserting expectations, on the result. The andExpect method asserts expectations on the result. In this example, we are checking for a successful response that contains the expected number of books.
    }
    
    @Test
    void findOneShouldReturnValidBook() throws Exception {
        Mockito.when(this.bookService.findOne(1)).thenReturn(getBooks().get(0));

        mvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("97 Things Every Java Programmer Should Know"))
                .andExpect(jsonPath("$.author").value("Kevlin Henney, Trisha Gee"))
                .andExpect(jsonPath("$.publisher").value("OReilly Media, Inc."))
                .andExpect(jsonPath("$.releaseDate").value("May 2020"))
                .andExpect(jsonPath("$.isbn").value("9781491952696"))
                .andExpect(jsonPath("$.topic").value("Java"));
    }
    
    //when app is not running, run `cd {dir}/mvn spring-boot:run`
    //mvn -Dtest=BookControllerTest#findAllShouldReturnAllBooks test
    //to run all tests -> `mvn test`
}
