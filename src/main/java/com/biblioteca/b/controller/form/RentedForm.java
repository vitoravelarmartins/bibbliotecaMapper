package com.biblioteca.b.controller.form;


import com.biblioteca.b.exception.EntityNotFoundException;
import com.biblioteca.b.model.Book;
import com.biblioteca.b.model.Person;
import com.biblioteca.b.model.Rented;
import com.biblioteca.b.model.StatusRented;
import com.biblioteca.b.repository.BookRepository;
import com.biblioteca.b.repository.PersonRepository;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.websocket.OnMessage;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
public class RentedForm {

    @NotNull
    private Long person;
    @NotNull
    private Long book;
    @NotNull
    private LocalDateTime dateDelivery;


    public Rented convert(PersonRepository personRepository, BookRepository bookRepository) {

        Optional<Book> bookOptional = Optional.ofNullable((bookRepository.findById(book)
                .orElseThrow(() -> new EntityNotFoundException("ID livro " + book + " não encontrado"))));

        Optional<Person> personOptional = Optional.ofNullable(personRepository.findById(person)
                .orElseThrow(() -> new EntityNotFoundException("ID usuario " + person + ", não encontrado")));



    //Optional<Book> bookOptional = bookRepository.findById(book);
        return new Rented(personOptional.get(), bookOptional.get(), dateDelivery);
    }
}
