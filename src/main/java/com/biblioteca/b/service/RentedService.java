package com.biblioteca.b.service;

import com.biblioteca.b.controller.dto.RentedDto;
import com.biblioteca.b.controller.form.RentedForm;
import com.biblioteca.b.exception.EntityNotFoundException;
import com.biblioteca.b.model.Book;
import com.biblioteca.b.model.Rented;
import com.biblioteca.b.model.StatusBook;
import com.biblioteca.b.repository.BookRepository;
import com.biblioteca.b.repository.PersonRepository;
import com.biblioteca.b.repository.RentedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class RentedService {


    @Autowired
    private RentedRepository rentedRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BookRepository bookRepository;


    public ResponseEntity<RentedDto> findById(Long id){
        Optional<Rented> rented = rentedRepository.findById(id);
       Rented rented1= rentedRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("ID "+id+" não encontrado"));
        return ResponseEntity.ok(new RentedDto((rented1)));
    }

    public List<RentedDto> findAll(String bookTitle) {

        if (bookTitle == null) {
            List<Rented> renteds = rentedRepository.findAll();
          return Rented.converter(renteds);
        } else {
            List<Rented> renteds = rentedRepository.findByBook_Title(bookTitle);
           return Rented.converter(renteds);
        }

    }

    public ResponseEntity<RentedDto> renting(RentedForm rentedForm,
                                          UriComponentsBuilder uriComponentsBuilder){

        Optional<Book> book = Optional.ofNullable((bookRepository.findById(rentedForm.getBook())
                .orElseThrow(() -> new EntityNotFoundException("ID livro " + rentedForm.getBook() + ", não encontrado"))));


        if(book.get().getStatus().equals(StatusBook.INDISPONIVEL)){
            return ResponseEntity.notFound().build();
        }else{
            book.get().getAmount();
            book.get().setAmount(book.get().getAmount()-1);
            System.out.println(book.get().getAmount());
            Rented rented = rentedForm.convert(personRepository,bookRepository);
            rentedRepository.save(rented);
            URI uri = uriComponentsBuilder.path("/locacoes/{id}").buildAndExpand(rented.getId()).toUri();
            return ResponseEntity.created(uri).body(new RentedDto(rented));
        }
    }
}
