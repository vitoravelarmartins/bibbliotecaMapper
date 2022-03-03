package com.biblioteca.b.service;

import com.biblioteca.b.controller.dto.PersonDto;
import com.biblioteca.b.controller.form.PersonForm;
import com.biblioteca.b.mapper.PersonMapper;
import com.biblioteca.b.model.Person;
import com.biblioteca.b.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class SignUpService {

    @Autowired
    public PersonRepository personRepository;

    @Autowired
    public PersonMapper personMapper;

    public ResponseEntity<?> register(PersonForm personForm,
                                      UriComponentsBuilder uriComponentsBuilder) {
        Optional<Person> byEmail = personRepository.findByEmail(personForm.getEmail());
        if (byEmail.isPresent()) {
            return new ResponseEntity<>("E-mail já cadastrado", HttpStatus.valueOf(409));
        } else {
            Person savedPerson = personRepository.save(personMapper.formToPerson(personForm));
            URI uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(savedPerson.getIdPerson()).toUri();
            return ResponseEntity.created(uri).body(personMapper.personToDto(savedPerson));
        }
    }
}
