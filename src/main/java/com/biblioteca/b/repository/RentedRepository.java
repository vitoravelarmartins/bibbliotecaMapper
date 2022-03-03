package com.biblioteca.b.repository;

import com.biblioteca.b.model.Rented;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RentedRepository extends JpaRepository<Rented, Long> {

    //List<Rented> findByBook_Title(String bookTitle);

    // Page<Rented> findByBook_Title(String bookTitle, Pageable pageable);

    Page<Rented> findByPerson_IdPerson(Long id, Pageable pageable);

    Page<Rented> findByPerson_IdPersonAndBook_Title(Long idUser, String bookTitle, Pageable pageable);

    Rented findByIdRentedAndPerson_IdPerson(Long id,Long idUser);
}
