package com.biblioteca.b.service;

import com.biblioteca.b.model.Person;
import com.biblioteca.b.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SecurityCheck {

    @Autowired
    public PersonRepository personRepository;


    public boolean check(String idUser, Authentication authentication){
        Long principal = (Long) authentication.getPrincipal();
        personRepository.findById(Long.valueOf(idUser));
        Long idLong = Long.valueOf(idUser);
        if(idUser == null){
            return false;
        }else if(idLong == principal){
            return true;
        }else {
            return false;
        }
    }
}
