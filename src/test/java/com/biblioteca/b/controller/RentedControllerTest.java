package com.biblioteca.b.controller;

import com.biblioteca.b.model.*;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestEntityManager
@Transactional
class RentedControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestEntityManager testEntityManager;

    private String tonkenInz;
    private Rented rentedInz;
    private Book bookInz;
    private Person personInz;

    @BeforeEach
    public void initialBook() throws Exception {
        this.personInz = new Person();
        this.personInz.setFirstName("personF");
        this.personInz.setLastName("pesonL");
        this.personInz.setEmail("email@email.com");
        this.personInz.setPasswordKey("$2a$10$zzkqMglP4a79tHwLvzTmt.ASlKF1XiRZXc8rqhZhXnNb26BR0WxgO");
        this.personInz.setStatusPerson(StatusUser.SEM_LIVRO);
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

        this.bookInz = new Book();
        this.bookInz.setTitle("bookT");
        this.bookInz.setAuthor("bookA");
        this.bookInz.setStatusBook(StatusBook.DISPONIVEL);
        this.bookInz.setAmount(10);
        testEntityManager.persist(bookInz);

        LocalDateTime localDateTime = LocalDateTime.now();

        this.rentedInz = new Rented();
        this.rentedInz.setBook(bookInz);
        this.rentedInz.setPerson(personInz);
        this.rentedInz.setDateRent(localDateTime.plusYears(1));
        this.rentedInz.setDateDelivery(localDateTime.plusYears(2));
        this.rentedInz.setStatusRented(StatusRented.NO_PRAZO);
        testEntityManager.persist(rentedInz);
    }

    @Test
    public void deveRetonarRentedPeloId() throws Exception {
        System.out.println("ID person: "+personInz.getIdPerson());
        System.out.println("ID Rented: "+rentedInz.getIdRented());

        Long idUser = personInz.getIdPerson();

        URI uri = new URI("/users/"+personInz.getIdPerson()+"/locacoes/"+rentedInz.getIdRented());

        mockMvc.perform((MockMvcRequestBuilders
                        .get(uri).header(HttpHeaders.AUTHORIZATION, "Bearer " + tonkenInz)
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

    }

    @Test
    void details() {
    }

    @Test
    void list() {
    }

    @Test
    void renting() {
    }

    @Test
    void delivery() {
    }

    @Test
    void detailsLease() {
    }

    @Test
    void detailsLeaseFindId() {
    }
}