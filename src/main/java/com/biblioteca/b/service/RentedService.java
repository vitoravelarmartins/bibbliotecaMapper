package com.biblioteca.b.service;

import com.biblioteca.b.config.security.TokenService;
import com.biblioteca.b.controller.dto.RentedDto;
import com.biblioteca.b.controller.form.RentedForm;
import com.biblioteca.b.exception.EntityNotFoundException;
import com.biblioteca.b.model.*;
import com.biblioteca.b.repository.BookRepository;
import com.biblioteca.b.repository.LeaseHistoryRepository;
import com.biblioteca.b.repository.PersonRepository;
import com.biblioteca.b.repository.RentedRepository;
import com.biblioteca.b.service.tools.VerifyBook;
import com.biblioteca.b.service.tools.VerifyRented;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class RentedService {


    @Autowired
    private RentedRepository rentedRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LeaseHistoryRepository leaseHistoryRepository;
    @Autowired
    private TokenService tokenService;


    public ResponseEntity<RentedDto> findById(Long id) {
        Rented rented = rentedRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID " + id + " não encontrado"));
        return ResponseEntity.ok(new RentedDto((rented)));
    }

    public Page<RentedDto> findAll(String idUserStr, String bookTitle, Pageable pageable) {
        Long idUser = Long.valueOf(idUserStr);
        if (bookTitle == null) {

            Page<Rented> byPerson_id = rentedRepository.findByPerson_Id(idUser, pageable);
            return Rented.converter(byPerson_id);
        } else {
            Page<Rented> byPerson_idAndBook_title = rentedRepository.findByPerson_IdAndBook_Title(idUser, bookTitle, pageable);
            return Rented.converter(byPerson_idAndBook_title);
        }


    }

    public ResponseEntity<?> renting(String idUserStr, RentedForm rentedForm, UriComponentsBuilder uriComponentsBuilder) {

        Long idUser = Long.valueOf(idUserStr);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        System.out.println("ID de quem esta autenticado: " + authentication.getPrincipal());
        Optional<Book> book = Optional.ofNullable((bookRepository.findById(
                rentedForm.getBook()).orElseThrow(() -> new EntityNotFoundException("ID livro " + rentedForm.getBook() + ", não encontrado"))));

            if (book.get().getStatus().equals(StatusBook.INDISPONIVEL) || book.get().getAmount() <= 0) {
            return new ResponseEntity<>("No momento o livro " + book.get().getTitle() + " esta indisponível.", HttpStatus.ACCEPTED);
        } else {
            Rented rented = rentedForm.convert(idUser, personRepository, rentedForm.getBook(), bookRepository);
            rented.getPerson().setStatus(StatusUser.COM_LIVRO);
            rented.getBook().setAmount(rented.getBook().getAmount() - 1);


            Rented rentedCreate = rentedRepository.save(rented);

            URI uri = uriComponentsBuilder.path("/users/{idUser}/locacoes/{id}").buildAndExpand(idUser, rentedCreate.getId()).toUri();
            rented.setUrlAvatar(uri.toString());
            new VerifyRented().toolVerifyRented(rentedRepository, personRepository);
            new VerifyBook().toolVerifyBook(bookRepository);
            return ResponseEntity.created(uri).body(new RentedDto(rented));
            // return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<?> delivery(String idUserStr, String idRentedStr) {
        //Long id = Long.valueOf(idUserStr);

        Long id = Long.valueOf(idRentedStr);
        Rented rented = rentedRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID Locação " + id + ", não encontrado"));


        LeaseHistory leaseHistory = new LeaseHistory(rented);
        leaseHistoryRepository.save(leaseHistory);

        rented.getBook().setAmount(rented.getBook().getAmount() + 1);
        rentedRepository.save(rented);

        Person personbyId = personRepository.getById(rented.getPerson().getId());
        personbyId.setStatus(StatusUser.SEM_LIVRO);
        personRepository.save(personbyId);

        rentedRepository.deleteById(rented.getId());

        new VerifyRented().toolVerifyRented(rentedRepository, personRepository);
        new VerifyBook().toolVerifyBook(bookRepository);

        return ResponseEntity.ok().body(new RentedDto(leaseHistory));

    }
}
