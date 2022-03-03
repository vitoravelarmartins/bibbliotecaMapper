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

@DataJpaTest
@ActiveProfiles("test")
class RentedRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private RentedRepository rentedRepository;

    private Person personInz;
    private Rented rentedInz;
    private Book bookInz;
    private Pageable pageableInz;

    @BeforeEach
    public void initialPerson() {
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

        this.rentedInz = new Rented();
        rentedInz.setDateRent(LocalDateTime.now());
        rentedInz.setDateDelivery(LocalDateTime.now());
        rentedInz.setStatusRented(StatusRented.NO_PRAZO);
        rentedInz.setBook(bookInz);
        rentedInz.setPerson(personInz);
        testEntityManager.persist(rentedInz);

        int page = 0;
        int qtdForPage = 1;
        this.pageableInz = PageRequest.of(0, 10);
    }

    @Test
    public void deveRetornnarOsAlugadosPeloIdDoPesoa() {
        Page<Rented> byPerson_idPerson = rentedRepository.findByPerson_IdPerson(personInz.getIdPerson(),pageableInz);
       Rented rented = byPerson_idPerson.stream().findFirst().get();
        Assertions.assertNotNull(byPerson_idPerson);
        Assertions.assertEquals(rented.getPerson().getFirstName(), "personF");
        Assertions.assertEquals(rented.getBook().getTitle(), "bookT");
    }

    @Test
    public void deveRetormarOsAlugadosPeloIdDaPessoaETituloDeLivro() {
        Page<Rented> byPerson_idPersonAndBook_title = rentedRepository.findByPerson_IdPersonAndBook_Title(personInz.getIdPerson(), "bookT", pageableInz);
        String title = byPerson_idPersonAndBook_title.stream().findFirst().get().getBook().getTitle();
        String firstName = byPerson_idPersonAndBook_title.stream().findFirst().get().getPerson().getFirstName();
        Assertions.assertNotNull(byPerson_idPersonAndBook_title);
        Assertions.assertEquals(firstName,"personF");
        Assertions.assertEquals(title,"bookT");
    }
}