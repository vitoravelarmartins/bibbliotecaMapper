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
class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void deveRetonarUmUsuarioCriado() throws Exception {
        URI uri = new URI("/signup");
        String json = "{\"firstName\":\"personF\",\"lastName\":\"pesonL\",\"email\":\"email@email.com\",\"passwordKey\":\"12345678\"}";

        MvcResult mvcResult = mockMvc.perform((MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().is(201)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        String substring = contentAsString.substring(27, 34);
        Assertions.assertEquals("personF", substring);
    }


}