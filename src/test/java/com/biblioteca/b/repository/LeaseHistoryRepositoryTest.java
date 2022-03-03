package com.biblioteca.b.repository;

import com.biblioteca.b.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class LeaseHistoryRepositoryTest {

    @Autowired
    private LeaseHistoryRepository leaseHistoryRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private LeaseHistory leaseHistoryInz;
    private Book bookInz;
    private Pageable pageableInz;
    private Person personInz;

    @BeforeEach
    public void initialLease() {

        this.bookInz = new Book();
        bookInz.setTitle("bookT");
        bookInz.setAuthor("bookA");
        bookInz.setStatusBook(StatusBook.DISPONIVEL);
        bookInz.setAmount(10);
        testEntityManager.persist(bookInz);

        this.personInz = new Person();
        personInz.setFirstName("personF");
        personInz.setLastName("pesonL");
        personInz.setEmail("email@email.com");
        personInz.setPasswordKey("123456789");
        personInz.setStatusPerson(StatusUser.SEM_LIVRO);
        testEntityManager.persist(personInz);

        this.leaseHistoryInz = new LeaseHistory();
        leaseHistoryInz.setBook(bookInz);
        leaseHistoryInz.setPerson(personInz);
        leaseHistoryInz.setDateRent(LocalDateTime.now());
        leaseHistoryInz.setDateDelivery(LocalDateTime.now());
        testEntityManager.persist(leaseHistoryInz);

        int page = 0;
        int qtdForPage = 1;
        this.pageableInz = PageRequest.of(0, 10);
    }

    @Test
    void deveriaCarregarHistoricoPorPessoa() {
        Page<LeaseHistory> byPerson_idPerson = leaseHistoryRepository.findByPerson_IdPerson(personInz.getIdPerson(), pageableInz);
        Person person = byPerson_idPerson.stream().findFirst().get().getPerson();
        Assertions.assertNotNull(byPerson_idPerson);
        Assertions.assertEquals(person.getFirstName(), "personF");

    }

    @Test
    void deveriaCarregarHistoricoPorPessoaETituloLivro() {
        Page<LeaseHistory> byPerson_idPersonAndBook_title = leaseHistoryRepository.findByPerson_IdPersonAndBook_Title(
                personInz.getIdPerson(), bookInz.getTitle(), pageableInz);
        LeaseHistory leaseHistory = byPerson_idPersonAndBook_title.stream().findFirst().get();

        Assertions.assertNotNull(byPerson_idPersonAndBook_title);
        Assertions.assertEquals(leaseHistory.getPerson().getFirstName(), "personF");
        Assertions.assertEquals(leaseHistory.getBook().getTitle(), "bookT");

    }
}