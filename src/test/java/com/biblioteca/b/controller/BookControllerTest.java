package com.biblioteca.b.controller;

import com.biblioteca.b.model.Book;
import com.biblioteca.b.model.Person;
import com.biblioteca.b.model.StatusBook;
import com.biblioteca.b.model.StatusUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestEntityManager
@Transactional
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestEntityManager testEntityManager;

    private String tonkenInz;

    @BeforeEach
    public void initialBook() throws Exception {
        Person personInz = new Person();
        personInz.setFirstName("personF");
        personInz.setLastName("pesonL");
        personInz.setEmail("email@email.com");
        personInz.setPasswordKey("$2a$10$zzkqMglP4a79tHwLvzTmt.ASlKF1XiRZXc8rqhZhXnNb26BR0WxgO");
        personInz.setStatusPerson(StatusUser.SEM_LIVRO);
        testEntityManager.persist(personInz);

        URI uri = new URI("/signin");
        String json = "{\"email\":\"email@email.com\",\"passwordKey\":\"12345678\"}";

        MvcResult mvcResult = mockMvc
                .perform((MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString().substring(10, 175);
        this.tonkenInz = contentAsString;

        Book bookInz = new Book();
        bookInz.setTitle("bookT");
        bookInz.setAuthor("bookA");
        bookInz.setStatusBook(StatusBook.DISPONIVEL);
        bookInz.setAmount(10);
        testEntityManager.persist(bookInz);
    }

    @Test
    public void deveriaRetornarListaDeLivros() throws Exception {
        URI uri = new URI("/livro");

        MvcResult mvcResult = mockMvc.perform((MockMvcRequestBuilders
                        .get(uri).header(HttpHeaders.AUTHORIZATION, "Bearer " + tonkenInz)
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

        String contentType = mvcResult.getResponse().getContentAsString();
        String substring = contentType.substring(45, 50);
        Assertions.assertEquals(substring, "bookT");
    }

    @Test
    public void deveRetornarLivroCriado() throws Exception {
        URI uri = new URI("/livro");
        String json = "{\"title\":\"bookTeste\",\"author\":\"athorTeste\",\"amount\":10}";

        MvcResult mvcResult = mockMvc.perform((MockMvcRequestBuilders
                        .post(uri).header(HttpHeaders.AUTHORIZATION, "Bearer " + tonkenInz)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(201)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        String substring = contentAsString.substring(33, 42);
        Assertions.assertEquals("bookTeste", substring);
    }
}