package com.biblioteca.b.service;

import com.biblioteca.b.config.security.TokenService;
import com.biblioteca.b.controller.dto.RentedDto;
import com.biblioteca.b.controller.form.RentedForm;
import com.biblioteca.b.exception.EntityNotFoundException;
import com.biblioteca.b.mapper.RentedMapper;
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
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    @Autowired
    private RentedMapper rentedMapper;


    public ResponseEntity<RentedDto> findById(Long id) {
        Rented rented = rentedRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID " + id + " não encontrado"));
        return ResponseEntity.ok(rentedMapper.rentedToDto(rented));
    }

    public Page<RentedDto> findAll(String idUserStr, String bookTitle, Pageable pageable) {
        Long idUser = Long.valueOf(idUserStr);
        if (bookTitle == null) {
            return rentedRepository.findByPerson_IdPerson(idUser, pageable).map(rentedMapper::rentedToDto);
        } else {
            return rentedRepository.findByPerson_IdPersonAndBook_Title(idUser, bookTitle, pageable)
                    .map(rentedMapper::rentedToDto);
        }


    }

    public ResponseEntity<?> renting(String idUserStr,
                                     RentedForm rentedForm,
                                     UriComponentsBuilder uriComponentsBuilder) {

        Long idUser = Long.valueOf(idUserStr);
        Optional<Person> optionalPerson = personRepository.findById(idUser);

        Optional<Book> book = Optional.ofNullable((bookRepository.findById(rentedForm.getBook())
                .orElseThrow(() -> new EntityNotFoundException("ID livro " + rentedForm.getBook() + ", não encontrado"))));


        if (book.get().getStatusBook().equals(StatusBook.INDISPONIVEL) || book.get().getAmount() <= 0) {
            return new ResponseEntity<>("No momento o livro " + book.get().getTitle() + " esta indisponível. ", HttpStatus.ACCEPTED);
        } else {
            Rented rented = rentedMapper.formToRented(optionalPerson.get(), rentedForm, book.get());
            Rented rentedCreate = rentedRepository.save(rented);
            URI uri = uriComponentsBuilder.path("/users/{idUser}/locacoes/{id}").buildAndExpand(idUser, rentedCreate.getIdRented()).toUri();

            rented.getBook().setAmount(rented.getBook().getAmount() - 1);

            new VerifyRented().toolVerifyRented(rentedRepository, personRepository);
            new VerifyBook().toolVerifyBook(bookRepository);

            return ResponseEntity.created(uri).body(rentedMapper.rentedToDto(rentedCreate));
        }

    }

    public ResponseEntity<?> delivery(String idUserStr,
                                      String idRentedStr,
                                      UriComponentsBuilder uriComponentsBuilder) {
        Long idRented = Long.valueOf(idRentedStr);
        Long idUser = Long.valueOf(idUserStr);

        Rented rented = rentedRepository.findById(idRented)
                .orElseThrow(() -> new EntityNotFoundException("ID Locação " + idRented + ", não encontrado"));

        LeaseHistory leaseHistory = new LeaseHistory(rented);
        LeaseHistory leaseHistorySave = leaseHistoryRepository.save(leaseHistory);

        rented.getBook().setAmount(rented.getBook().getAmount() + 1);
        rentedRepository.save(rented);

        Person personbyId = personRepository.getById(idUser);
        personbyId.setStatusPerson(StatusUser.SEM_LIVRO);
        Person savePerson = personRepository.save(personbyId);

        rentedRepository.deleteById(rented.getIdRented());

        new VerifyRented().toolVerifyRented(rentedRepository, personRepository);
        new VerifyBook().toolVerifyBook(bookRepository);

        URI uri = uriComponentsBuilder.path("/users/{idUser}/locacoes/leaseHistory/{id}").buildAndExpand(savePerson.getIdPerson(), leaseHistorySave.getIdLease()).toUri();
        return ResponseEntity.created(uri).body(rentedMapper.leaseToRentedDto(leaseHistory));

    }

    public Page<RentedDto> leaseFind(String idUserStr, String bookTitle, Pageable pageable) {
        Long idUser = Long.valueOf(idUserStr);

        if (bookTitle == null) {
            return leaseHistoryRepository.findByPerson_IdPerson(idUser, pageable)
                    .map(rentedMapper::leaseToRentedDto);
        } else {
            return leaseHistoryRepository.findByPerson_IdPersonAndBook_Title(idUser, bookTitle, pageable)
                    .map(rentedMapper::leaseToRentedDto);
        }
    }

    public ResponseEntity<RentedDto> leaseFind(String idLeaseString) {
        Long idLease = Long.valueOf(idLeaseString);
        LeaseHistory leaseHistory = leaseHistoryRepository.findById(idLease).orElseThrow(() -> new EntityNotFoundException("ID " + idLease + ", não encontrado"));
        return ResponseEntity.ok(rentedMapper.leaseToRentedDto(leaseHistory));


    }
}
