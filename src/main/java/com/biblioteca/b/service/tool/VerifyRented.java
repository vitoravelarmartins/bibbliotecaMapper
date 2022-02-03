package com.biblioteca.b.service.tool;

import ch.qos.logback.core.boolex.EvaluationException;
import com.biblioteca.b.model.Rented;
import com.biblioteca.b.model.StatusRented;
import com.biblioteca.b.model.StatusUser;
import com.biblioteca.b.repository.RentedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VerifyRented {

    public void toolVerifyRented(RentedRepository rentedRepository) {
        LocalDateTime dateNow = LocalDateTime.now();
        List<Rented> rentedList = rentedRepository.findAll();

        System.out.println(rentedList.stream().map(Rented::getStatus).collect(Collectors.toList()));

       rentedList.stream().filter(d -> d.getDateRent().compareTo(dateNow) < 0)
               .forEach(value -> System.out.println("Datas Atrasadas: "+value.getDateRent()));

        rentedList.stream().filter(d -> d.getDateRent().compareTo(dateNow) < 0)
                .forEach(value -> value.setStatus(StatusRented.ATRASADO));

        rentedList.stream().filter(d -> d.getDateRent().compareTo(dateNow) > 0)
                .forEach(value -> value.setStatus(StatusRented.NO_PRAZO));

        rentedList.stream().filter(d -> d.getStatus().equals(StatusRented.ATRASADO))
                .forEach(value -> value.getPerson().setStatus(StatusUser.DEVEDOR));


        rentedList.stream().filter(d -> d.getStatus().equals(StatusRented.ATRASADO))
               .forEach(value -> rentedRepository.save(value));




        System.out.println(rentedList.stream().map(Rented::getStatus).collect(Collectors.toList()));

//        LocalDateTime localDateTime5 = LocalDateTime.of(2022, 02, 01, 16, 18, 00);
//        dateNow.compareTo(localDateTime5);
//        System.out.println(dateNow.compareTo(localDateTime5));



    }
}
