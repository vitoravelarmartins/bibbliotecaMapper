package com.biblioteca.b;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@Slf4j
@SpringBootApplication
@EnableSpringDataWebSupport
@OpenAPIDefinition
public class BApplication {

	public static void main(String[] args) {
		SpringApplication.run(BApplication.class, args);

	}


	
}
