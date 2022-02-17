package com.biblioteca.b.model;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Rented {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRented;
    @ManyToOne
    private Person person;
    @ManyToOne
    private Book book;

    private LocalDateTime dateRent = LocalDateTime.now();
    private LocalDateTime dateDelivery;
    @Enumerated(EnumType.STRING)
    private StatusRented statusRented = StatusRented.NO_PRAZO;



    public Rented() {
    }
}
