package com.biblioteca.b.service.tool;

import com.biblioteca.b.model.Person;
import com.biblioteca.b.model.Rented;
import com.biblioteca.b.model.StatusRented;
import com.biblioteca.b.model.StatusUser;
import com.biblioteca.b.repository.BookRepository;
import com.biblioteca.b.repository.PersonRepository;
import com.biblioteca.b.repository.RentedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VerifyRented {

        public void toolVerifyRented(RentedRepository rentedRepository,
                                     PersonRepository personRepository) {

        LocalDateTime dateNow = LocalDateTime.now();
        List<Rented> rentedList = rentedRepository.findAll();

            rentedList.stream().parallel()
                    .filter(d -> d.getDateDelivery().compareTo(dateNow) > 0)
                    .forEach(r -> statusSettings(r, rentedRepository,
                            r.getPerson().getId(),personRepository,StatusRented.NO_PRAZO,StatusUser.COM_LIVRO));

        rentedList.stream().parallel()
                .filter(d -> d.getDateDelivery().compareTo(dateNow) < 0)
                .forEach(r -> statusSettings(r, rentedRepository,
                        r.getPerson().getId(),personRepository,StatusRented.ATRASADO,StatusUser.DEVEDOR));

        System.out.println("VERIFY RENTED: "+"Verificando com sucesso todos so livros locados e setando atrasados.");

    }

    public void statusSettings(Rented rented,
                               RentedRepository rentedRepository,
                               Long id, PersonRepository personRepository,StatusRented statusRented, StatusUser statusUser) {
        rented.setStatus(statusRented);
        Optional<Person> byId = personRepository.findById(id);
        byId.get().setStatus(statusUser);
        personRepository.save(byId.get());
        rentedRepository.save(rented);

    }

}

//        LocalDateTime localDateTime5 = LocalDateTime.of(2022, 02, 01, 16, 18, 00);
//        dateNow.compareTo(localDateTime5);
//        System.out.println(dateNow.compareTo(localDateTime5));
