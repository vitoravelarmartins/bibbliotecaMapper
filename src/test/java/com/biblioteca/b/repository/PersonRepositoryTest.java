package com.biblioteca.b.repository;

import com.biblioteca.b.model.Person;
import com.biblioteca.b.model.StatusUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class PersonRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private PersonRepository personRepository;
    private Person personInz;


    @BeforeEach
    public void initialPerson() {
        this.personInz = new Person();
        personInz.setFirstName("personF");
        personInz.setLastName("pesonL");
        personInz.setEmail("email@email.com");
        personInz.setPasswordKey("123456789");
        personInz.setStatusPerson(StatusUser.SEM_LIVRO);
        testEntityManager.persist(personInz);

    }

    @Test
    public void deveRetornarPessoaPeloEmailDaPessoa() {
        Optional<Person> byEmail = personRepository.findByEmail("email@email.com");
        Assertions.assertNotNull(byEmail);
        Assertions.assertEquals("email@email.com", byEmail.get().getEmail());
    }

}