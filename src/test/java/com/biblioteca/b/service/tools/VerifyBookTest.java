package com.biblioteca.b.service.tools;

import com.biblioteca.b.model.Book;
import com.biblioteca.b.model.StatusBook;
import com.biblioteca.b.repository.BookRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestEntityManager
@Transactional
class VerifyBookTest {

    @Autowired
    private TestEntityManager testEntityManager;
    private Book bookInz;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void initialBook() throws Exception {
        this.bookInz = new Book();
        bookInz.setTitle("bookT");
        bookInz.setAuthor("bookA");
        bookInz.setStatusBook(StatusBook.DISPONIVEL);
        bookInz.setAmount(0);
        testEntityManager.persist(bookInz);
    }

    @Test
    public void deveSetarLivroComoIndisponivelAutomaticamente() {
        VerifyBook verifyBook = new VerifyBook();
        verifyBook.toolVerifyBook(bookRepository);
        Assertions.assertEquals(StatusBook.INDISPONIVEL, bookInz.getStatusBook());
    }

    @Test
    public void deveSetarLivroComoDisponivelAutomaticamente(){
        bookInz.setAmount(5);
        VerifyBook verifyBook = new VerifyBook();
        verifyBook.toolVerifyBook(bookRepository);
        Assertions.assertEquals(StatusBook.DISPONIVEL, bookInz.getStatusBook());
    }
}