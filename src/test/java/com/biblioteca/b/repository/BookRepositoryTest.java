package com.biblioteca.b.repository;

import com.biblioteca.b.model.Book;
import com.biblioteca.b.model.StatusBook;
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

//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ActiveProfiles("test")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private Book bookInz;
    private Pageable pageableInz;

    @BeforeEach
    public void initialRented() {
        this.bookInz = new Book();
        bookInz.setTitle("bookT");
        bookInz.setAuthor("bookA");
        bookInz.setStatusBook(StatusBook.DISPONIVEL);
        bookInz.setAmount(10);
        testEntityManager.persist(bookInz);

        int page = 0;
        int qtdForPage = 1;
        this.pageableInz = PageRequest.of(0, 10);
    }

    @Test
    public void deveriaCarregarLivroBuscarPeloSeuNome() {
        Page<Book> byTitle = bookRepository.findByTitle("bookT", pageableInz);
        String title = byTitle.stream().findFirst().get().getTitle();
        Assertions.assertNotNull(title);
        Assertions.assertEquals(title, "bookT");
    }

    @Test
    public void deveriaCarregarLivroBuscarPeloAuthor() {
        Page<Book> byAuthor = bookRepository.findByAuthor("bookA", pageableInz);
        String author = byAuthor.stream().findFirst().get().getAuthor();
        Assertions.assertNotNull(author);
        Assertions.assertEquals(author, "bookA");
    }

    @Test
    void deveriaCarregarLivroBuscarPeloTitleAutor() {
        Page<Book> byTitleAndAuthor = bookRepository.findByTitleAndAuthor("bookT", "bookA", pageableInz);
        String author = byTitleAndAuthor.stream().findFirst().get().getAuthor();
        String title = byTitleAndAuthor.stream().findFirst().get().getTitle();
        Long idBook = byTitleAndAuthor.stream().findFirst().get().getIdBook();
        Assertions.assertNotNull(byTitleAndAuthor);
        Assertions.assertEquals(title, "bookT");
        Assertions.assertEquals(author, "bookA");
    }


}