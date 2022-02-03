package com.biblioteca.b.service.tool;

import com.biblioteca.b.repository.BookRepository;
import com.biblioteca.b.repository.RentedRepository;
import com.biblioteca.b.service.tool.VerifyBook;
import com.biblioteca.b.service.tool.VerifyRented;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class AutomaticRun {

    @Autowired
    private RentedRepository rentedRepository;
    @Autowired
    private BookRepository bookRepository;


    @Scheduled(fixedDelay = 300000)
    public void runVerifyRented(){
        VerifyRented verifyRented = new VerifyRented();
        verifyRented.toolVerifyRented(rentedRepository);
    }
    @Scheduled(fixedDelay = 60000)
    public void runVerifyBook(){
        VerifyBook verifyBook = new VerifyBook();
        verifyBook.toolVerifyBook(bookRepository);
    }
}
