package com.biblioteca.b.model;


import com.biblioteca.b.controller.dto.PersonDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String passwordKey;
    @Enumerated(EnumType.STRING)
    private StatusUser status = StatusUser.SEM_LIVRO;

    public Person() {
    }

    public Person(String firstName, String lastName, String email, String passwordKeyCrypt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordKey =passwordKeyCrypt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return id.equals(person.id) && firstName.equals(person.firstName) && lastName.equals(person.lastName) && email.equals(person.email) && passwordKey.equals(person.passwordKey) && status == person.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, passwordKey, status);
    }

    public static List<PersonDto> convert(List<Person> persons){
        return persons.stream().map(PersonDto::new).collect(Collectors.toList());
    }
}
