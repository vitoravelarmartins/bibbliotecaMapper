package com.biblioteca.b.controller;

import com.biblioteca.b.model.Book;
import com.biblioteca.b.model.Person;
import com.biblioteca.b.model.StatusUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
class SigninControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestEntityManager testEntityManager;

    @Value("${person.jwt.secret}")
    private String secret;

    private Book bookInz;
    private Person personInz;


    @BeforeEach
    public void initialPerson() {
        this.personInz = new Person();
        personInz.setFirstName("personF");
        personInz.setLastName("pesonL");
        personInz.setEmail("email@email.com");
        personInz.setPasswordKey("$2a$10$zzkqMglP4a79tHwLvzTmt.ASlKF1XiRZXc8rqhZhXnNb26BR0WxgO");
        personInz.setStatusPerson(StatusUser.SEM_LIVRO);
        testEntityManager.persist(personInz);
    }

    @Test
    public void deveriaDevolver401CasoDadsoDeAutenticacaoEstejaInvalidos() throws Exception {
        URI uri = new URI("/signin");
        String json = "{\"email\":\"invalido@email.com\",\"passwordKey\":\"123456789\"}";

        mockMvc
                .perform((MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(401))
                .andExpect(MockMvcResultMatchers.content().string("Usuário e/ou senha inválidos"));
    }

    @Test
    public void deveriaDevolverToken() throws Exception {
        URI uri = new URI("/signin");
        String json = "{\"email\":\"email@email.com\",\"passwordKey\":\"12345678\"}";

        MvcResult mvcResult = mockMvc
                .perform((MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(contentAsString.substring(10, 175));
        Assertions.assertNotNull(contentAsString);
        Assertions.assertEquals(claimsJws.getBody().getSubject(), personInz.getIdPerson().toString());
    }
}