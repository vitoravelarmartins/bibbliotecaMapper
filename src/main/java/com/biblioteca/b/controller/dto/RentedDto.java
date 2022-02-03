package com.biblioteca.b.controller.dto;

import com.biblioteca.b.model.Book;
import com.biblioteca.b.model.Person;
import com.biblioteca.b.model.Rented;
import com.biblioteca.b.model.StatusRented;
import com.biblioteca.b.repository.PersonRepository;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RentedDto {

    private Long id;
    private Person person;
    private Book book;
    private LocalDateTime dateRent;
    private LocalDateTime dateDelivery;
    private StatusRented status;

    public RentedDto(Rented rented){

        this.id = rented.getId();
        this.person = rented.getPerson();
        this.book= rented.getBook();
        this.dateRent=rented.getDateRent();
        this.dateDelivery=rented.getDateDelivery();
        this.status=rented.getStatus();

    }
}
