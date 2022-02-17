package com.biblioteca.b.controller.dto;

import com.biblioteca.b.model.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RentedDto {

    private Long idRented;
    private PersonDto person;
    private Book book;
    private LocalDateTime dateRent;
    private LocalDateTime dateDelivery;
    private StatusRented statusRented;
}
