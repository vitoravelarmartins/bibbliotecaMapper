package com.biblioteca.b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


@SpringBootApplication
@EnableSpringDataWebSupport
public class BApplication {

	public static void main(String[] args) {
		SpringApplication.run(BApplication.class, args);

	}


	
}
