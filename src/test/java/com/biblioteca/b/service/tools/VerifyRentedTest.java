package com.biblioteca.b.service.tools;

import com.biblioteca.b.model.*;
import com.biblioteca.b.repository.PersonRepository;
import com.biblioteca.b.repository.RentedRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestEntityManager
@Transactional
class VerifyRentedTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private RentedRepository rentedRepository;

    @Autowired
    private PersonRepository personRepository;


    private Rented rentedInz;
    private Book bookInz;
    private Person personInz;
    private LeaseHistory leaseHistoryInz;

    @BeforeEach
    public void initialBook() throws Exception {
        this.personInz = new Person();
        this.personInz.setFirstName("personF");
        this.personInz.setLastName("pesonL");
        this.personInz.setEmail("email@email.com");
        this.personInz.setPasswordKey("$2a$10$zzkqMglP4a79tHwLvzTmt.ASlKF1XiRZXc8rqhZhXnNb26BR0WxgO");
        this.personInz.setStatusPerson(StatusUser.SEM_LIVRO);
        testEntityManager.persist(personInz);

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
    public void deveSetarUsuarioComoDevedorELivroComoAtrasado(){
        rentedInz.setDateDelivery(LocalDateTime.parse("1995-02-16T14:50:00"));
        VerifyRented verifyRented = new VerifyRented();
        verifyRented.toolVerifyRented(rentedRepository,personRepository);

        Assertions.assertEquals(StatusUser.DEVEDOR,personInz.getStatusPerson());
        Assertions.assertEquals(StatusRented.ATRASADO,rentedInz.getStatusRented());
    }

    @Test
    public void deveSetarUsuarioComLivroELivroComoNoPrazo(){
        rentedInz.setDateDelivery(LocalDateTime.now().plusYears(3));
        VerifyRented verifyRented = new VerifyRented();
        verifyRented.toolVerifyRented(rentedRepository,personRepository);

        Assertions.assertEquals(StatusUser.COM_LIVRO,personInz.getStatusPerson());
        Assertions.assertEquals(StatusRented.NO_PRAZO,rentedInz.getStatusRented());
    }

}