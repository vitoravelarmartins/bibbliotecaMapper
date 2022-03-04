package com.biblioteca.b.controller;

import com.biblioteca.b.config.security.TokenService;
import com.biblioteca.b.controller.form.SigninForm;
import com.biblioteca.b.model.*;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import java.net.URI;
import java.time.LocalDateTime;

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
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private String tonkenInz;
    private Rented rentedInz;
    private Book bookInz;
    private Person personInz;
    private LeaseHistory leaseHistoryInz;
    private SigninForm signinFormInz;

    @BeforeEach
    public void initialBook() throws Exception {
        this.personInz = new Person();
        this.personInz.setFirstName("personF");
        this.personInz.setLastName("pesonL");
        this.personInz.setEmail("email@email.com");
        this.personInz.setPasswordKey("$2a$10$zzkqMglP4a79tHwLvzTmt.ASlKF1XiRZXc8rqhZhXnNb26BR0WxgO");
        this.personInz.setStatusPerson(StatusUser.SEM_LIVRO);
        testEntityManager.persist(personInz);

        this.signinFormInz = new SigninForm();
        signinFormInz.setEmail(personInz.getEmail());
        signinFormInz.setPasswordKey("12345678");

        UsernamePasswordAuthenticationToken dataSignin = signinFormInz.converter();
        Authentication authentication = authenticationManager.authenticate(dataSignin);
        String token = tokenService.generateToken(authentication);
        this.tonkenInz = token;

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

        this.leaseHistoryInz = new LeaseHistory();
        this.leaseHistoryInz.setDateDelivery(localDateTime);
        this.leaseHistoryInz.setBook(bookInz);
        this.leaseHistoryInz.setPerson(personInz);
        this.leaseHistoryInz.setDateRent(localDateTime);
        this.leaseHistoryInz.setStatusLease(StatusRented.NO_PRAZO);
        testEntityManager.persist(leaseHistoryInz);
    }

    @Test
    public void deveRetonarRentedPeloId() throws Exception {
        URI uri = new URI("/users/" + personInz.getIdPerson() + "/locacoes/" + rentedInz.getIdRented());

        MvcResult mvcResult = mockMvc.perform((MockMvcRequestBuilders
                        .get(uri).header(HttpHeaders.AUTHORIZATION, "Bearer " + tonkenInz)
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        String substring = contentAsString.substring(50, 57);
        Assertions.assertEquals(substring, "personF");

    }

    @Test
    public void deveRetornarUmaListaDeRented() throws Exception {
        URI uri = new URI("/users/" + personInz.getIdPerson() + "/locacoes/");

        MvcResult mvcResult = mockMvc.perform((MockMvcRequestBuilders
                        .get(uri).header(HttpHeaders.AUTHORIZATION, "Bearer " + tonkenInz)
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        String substring = contentAsString.substring(185, 190);
        Assertions.assertEquals(substring, "bookT");
    }

    @Test
    public void deveFazerRented() throws Exception {
        URI uri = new URI("/users/" + personInz.getIdPerson() + "/locacoes/locar");
        LocalDateTime localDateTime = LocalDateTime.now();
        String json = "{\"book\":" + bookInz.getIdBook() + ",\"dateDelivery\":\"" + localDateTime.plusYears(1) + "\"}";

        MvcResult mvcResult = mockMvc.perform((MockMvcRequestBuilders
                        .post(uri)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tonkenInz)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(201)).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        String substring = contentAsString.substring(71, 77);
        Assertions.assertEquals(substring, "pesonL");
    }

    @Test
    public void deveRetonarEntregaDoLivro() throws Exception {
        URI uri = new URI("/users/" + personInz.getIdPerson() + "/locacoes/" + rentedInz.getIdRented() + "/devolver");


        MvcResult mvcResult = mockMvc.perform((MockMvcRequestBuilders
                        .post(uri)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tonkenInz)
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(201)).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        String substring = contentAsString.substring(71, 77);
        Assertions.assertEquals(substring, "pesonL");

    }

    @Test
    public void deveRetonarUmaListaDeLease() throws Exception {
        URI uri = new URI("/users/" + personInz.getIdPerson() + "/locacoes/leaseHistory");

        MvcResult mvcResult = mockMvc.perform((MockMvcRequestBuilders
                        .get(uri)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tonkenInz)
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        String substring = contentAsString.substring(62, 69);
        Assertions.assertEquals(substring,"personF");
        System.out.println("bola: "+contentAsString);

    }

    @Test
    public void deveRetornarUmLeasePeloID() throws Exception {
        URI uri = new URI("/users/" + personInz.getIdPerson() + "/locacoes/leaseHistory/"+leaseHistoryInz.getIdLease());

        MvcResult mvcResult = mockMvc.perform((MockMvcRequestBuilders
                        .get(uri)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tonkenInz)
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        String substring = contentAsString.substring(115, 130);
        Assertions.assertEquals(substring,"email@email.com");

    }
}