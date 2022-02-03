package com.biblioteca.b.controller;


import com.biblioteca.b.controller.dto.PersonDto;
import com.biblioteca.b.controller.form.PersonForm;
import com.biblioteca.b.repository.PersonRepository;
import com.biblioteca.b.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/signup")
public class SignUpController {

   @Autowired
    public SignUpService singUpService;

//    @GetMapping
//    public List<PersonDto> lstPerson(){
//        List<Person> people = personRepository.findAll();
//        return Person.convert(people);
//    }

    @PostMapping
    @Transactional
    public ResponseEntity<PersonDto> register(@RequestBody @Valid PersonForm personForm,
                                              UriComponentsBuilder uriComponentsBuilder){
        return singUpService.register(personForm,uriComponentsBuilder);


    }
}
