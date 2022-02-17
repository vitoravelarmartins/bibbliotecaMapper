package com.biblioteca.b.controller.dto;

import com.biblioteca.b.model.StatusUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDto  {

    private Long idPerson;
    private String firstName;
    private String lastName;
    private StatusUser statusPerson;
    private String email;
}
