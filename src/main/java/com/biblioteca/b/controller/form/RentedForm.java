package com.biblioteca.b.controller.form;


import com.biblioteca.b.exception.EntityNotFoundException;
import com.biblioteca.b.model.Book;
import com.biblioteca.b.model.Person;
import com.biblioteca.b.model.Rented;
import com.biblioteca.b.repository.BookRepository;
import com.biblioteca.b.repository.PersonRepository;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
public class RentedForm {

    private Long person;
    @NotNull
    private Long book;
    @NotNull @FutureOrPresent
    private LocalDateTime dateDelivery;


    public Rented convert(Long idUser, PersonRepository personRepository, Long idBook, BookRepository bookRepository) {
        this.person = idUser;
        this.book = idBook;

        Optional<Person> personOptional = Optional.ofNullable(personRepository.findById(person)
                .orElseThrow(() -> new EntityNotFoundException("ID usuario " + person + ", não encontrado")));

        Optional<Book> bookOptional = Optional.ofNullable((bookRepository.findById(book)
                .orElseThrow(() -> new EntityNotFoundException("ID livro " + book + " não encontrado"))));

        return new Rented(personOptional.get(), bookOptional.get(), dateDelivery);
    }

}
