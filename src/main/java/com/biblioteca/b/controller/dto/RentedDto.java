package com.biblioteca.b.controller.dto;

import com.biblioteca.b.model.*;
import com.biblioteca.b.repository.PersonRepository;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class RentedDto {

    private Long id;
    private PersonDto person;
    private Book book;
    private LocalDateTime dateRent;
    private LocalDateTime dateDelivery;
    private StatusRented status;
    private String urlAvatar;

    public RentedDto(Rented rented){
        PersonDto personDto = new PersonDto(rented.getPerson());


        this.id = rented.getId();
        this.person = personDto;
        this.book= rented.getBook();
        this.dateRent=rented.getDateRent();
        this.dateDelivery=rented.getDateDelivery();
        this.status=rented.getStatus();
        this.urlAvatar = rented.getUrlAvatar();

    }

    public RentedDto(LeaseHistory leaseHistory){
        PersonDto personDto = new PersonDto(leaseHistory.getPerson());


        this.id = leaseHistory.getId();
        this.person = personDto;
        this.book= leaseHistory.getBook();
        this.dateRent=leaseHistory.getDateRent();
        this.dateDelivery=leaseHistory.getDateDelivery();
        this.status=leaseHistory.getStatus();


    }
}
