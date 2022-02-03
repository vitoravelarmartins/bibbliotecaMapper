package com.biblioteca.b.model;

import com.biblioteca.b.controller.dto.PersonDto;
import com.biblioteca.b.controller.dto.RentedDto;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class Rented {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Person person;
    @ManyToOne
    private Book book;

    private LocalDateTime dateRent = LocalDateTime.now();
    private LocalDateTime dateDelivery;
    @Enumerated(EnumType.STRING)
    private StatusRented status = StatusRented.NO_PRAZO;

    public Rented(Person person, Book book, LocalDateTime dateDelivery) {
        this.person = person;
        this.book = book;
        this.dateDelivery = dateDelivery;
    }

    public Rented() {
    }

    public Rented(Rented rented) {
    }


    public static List<RentedDto> converter(List<Rented> renteds){
        return renteds.stream().map(RentedDto::new).collect(Collectors.toList());
    }


}
