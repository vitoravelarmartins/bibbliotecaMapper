package com.biblioteca.b.model;


import com.biblioteca.b.controller.dto.PersonDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class Person implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPerson;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String passwordKey;
    @Enumerated(EnumType.STRING)
    private StatusUser statusPerson = StatusUser.SEM_LIVRO;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<ProfileTypes> profileTypes = new ArrayList<>();

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
        return idPerson.equals(person.idPerson) && firstName.equals(person.firstName) && lastName.equals(person.lastName) && email.equals(person.email) && passwordKey.equals(person.passwordKey) && statusPerson == person.statusPerson;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPerson, firstName, lastName, email, passwordKey, statusPerson);
    }


//    public static List<PersonDto> convert(List<Person> personList){
//        return personList.stream().map(PersonDto::new).collect(Collectors.toList());
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.profileTypes;
    }

    @Override
    public String getPassword() {
        return this.passwordKey;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
