package com.biblioteca.b.service.tool;

import com.biblioteca.b.model.Book;
import com.biblioteca.b.model.StatusBook;
import com.biblioteca.b.model.StatusRented;
import com.biblioteca.b.repository.BookRepository;

import java.util.List;

public class VerifyBook {

    public void toolVerifyBook(BookRepository bookRepository){
        List<Book> bookList = bookRepository.findAll();

        bookList.stream().filter(b -> b.getAmount() <= 0 )
                .forEach(value -> value.setStatus(StatusBook.INDISPONIVEL));

        bookList.stream().filter(b -> b.getAmount() <= 0 )
                .forEach(value -> System.out.println(value.getStatus()));

        bookList.stream().filter(b -> b.getAmount() <= 0 )
                .forEach(value -> bookRepository.save(value));

        System.out.println("BOOK: ESTOU VERIFICANDO OS BOOK");


    }
}
