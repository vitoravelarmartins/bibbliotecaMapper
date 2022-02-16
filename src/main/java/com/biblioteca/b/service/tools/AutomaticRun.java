package com.biblioteca.b.service.tools;


import com.biblioteca.b.repository.BookRepository;
import com.biblioteca.b.repository.PersonRepository;
import com.biblioteca.b.repository.RentedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@EnableScheduling
public class AutomaticRun {

    @Autowired
    private RentedRepository rentedRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PersonRepository personRepository;


    //Tempo "certo" 300000
    @Transactional
    @Scheduled(fixedRate = 60501)
    public void runVerifyRented() {
        VerifyRented verifyRented = new VerifyRented();
        verifyRented.toolVerifyRented(rentedRepository, personRepository);
    }

    //Tempo "certo" 60000
    @Transactional
    @Scheduled(fixedRate = 60000)
    public void runVerifyBook() {
        VerifyBook verifyBook = new VerifyBook();
        verifyBook.toolVerifyBook(bookRepository);
    }
}
