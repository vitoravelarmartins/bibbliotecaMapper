package com.biblioteca.b.model;

import com.biblioteca.b.controller.dto.RentedDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class LeaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLease;
    @ManyToOne
    private Person person;
    @ManyToOne
    private Book book;

    private LocalDateTime dateRent;
    private LocalDateTime dateDelivery= LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private StatusRented statusLease;

    public LeaseHistory() {
    }

    public LeaseHistory(Rented rented){
      this.person = rented.getPerson();
     this.book = rented.getBook();
      this.dateRent = rented.getDateRent();
      this.dateDelivery = getDateDelivery();
     this.statusLease = rented.getStatusRented();

  }

}
