package com.biblioteca.b.repository;

import com.biblioteca.b.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByFirstName(String firstName);
}
