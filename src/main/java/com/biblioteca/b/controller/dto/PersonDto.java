package com.biblioteca.b.controller.dto;

import com.biblioteca.b.model.Person;
import com.biblioteca.b.model.StatusUser;
import lombok.Getter;

import java.util.List;


@Getter
public class PersonDto  {
    private Long id;
    private String firstName;
    private String lastName;
    private StatusUser status;

    public PersonDto(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.status = person.getStatus();
    }


}
