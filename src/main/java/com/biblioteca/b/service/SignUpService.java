package com.biblioteca.b.service;

import com.biblioteca.b.controller.dto.PersonDto;
import com.biblioteca.b.controller.form.PersonForm;
import com.biblioteca.b.model.Person;
import com.biblioteca.b.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class SignUpService {

    @Autowired
    public PersonRepository personRepository;

    public ResponseEntity<PersonDto> register(PersonForm personForm,
                                              UriComponentsBuilder uriComponentsBuilder) {
        Person person = personForm.convert();
        personRepository.save(person);
        URI uri = uriComponentsBuilder.path("/person/{id}").buildAndExpand(person.getId()).toUri();
        return ResponseEntity.created(uri).body(new PersonDto(person));
    }
}
