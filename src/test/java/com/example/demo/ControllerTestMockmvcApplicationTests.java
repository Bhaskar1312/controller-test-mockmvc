package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//The @SpringBootTest annotation can be specified on a test class
//that runs Spring Boot–based tests. Using this annotation provides 
//many features over and above the regular Spring TestContext Framework. 
//@SpringBootTest:
//Uses SpringBootContextLoader as the default ContextLoader when no specific @ContextConfiguration(loader=...) is defined
//Automatically searches for a @SpringBootConfiguration when nested @Configuration is not used and no explicit classes are specified
//Allows custom environment properties to be defined using the properties attribute
//Allows application arguments to be defined using the args attribute
//Provides support for different webEnvironment modes, including the ability to start a fully running web server listening on a defined or random port
//Registers a TestRestTemplate and/or WebTestClient bean for use in web tests that are using a fully running web server

@SpringBootTest
class ControllerTestMockmvcApplicationTests {

	@Test
	void contextLoads() {
	}

}
