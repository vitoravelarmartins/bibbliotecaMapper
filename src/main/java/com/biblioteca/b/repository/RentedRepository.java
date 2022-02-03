package com.biblioteca.b.repository;

import com.biblioteca.b.model.Rented;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentedRepository extends JpaRepository<Rented,Long> {
    List<Rented> findByBook_Title(String bookTitle);
}
