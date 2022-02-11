package com.biblioteca.b.repository;

import com.biblioteca.b.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByFirstName(String firstName);

    Optional<Person> findByEmail(String username);
}
