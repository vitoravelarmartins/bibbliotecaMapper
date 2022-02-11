package com.biblioteca.b.service.tool;

import com.biblioteca.b.model.*;
import com.biblioteca.b.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerifyBook {

    public void toolVerifyBook(BookRepository bookRepository) {
        List<Book> bookList = bookRepository.findAll();

        bookList.stream()
                .filter(b -> b.getAmount() <= 0)
                .forEach(book -> statusSettings(bookRepository, book,StatusBook.INDISPONIVEL));

        bookList.stream()
                .filter(b -> b.getAmount() > 0)
                .forEach(book -> statusSettings(bookRepository, book,StatusBook.DISPONIVEL));

        System.out.println("BOOK: ESTOU VERIFICANDO OS BOOK");

    }

    public void statusSettings(BookRepository bookRepository, Book book, StatusBook statusBook) {
        book.setStatus(statusBook);
        bookRepository.save(book);
    }

}
