package com.biblioteca.b.repository;

import com.biblioteca.b.model.Rented;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;


public interface RentedRepository extends JpaRepository<Rented,Long> {
    List<Rented> findByBook_Title(String bookTitle);

    Page<Rented> findByBook_Title(String bookTitle, Pageable pageable);

    Page<Rented> findByPerson_Id(Long id, Pageable pageable);

    Page<Rented> findByPerson_IdAndBook_Title(Long idUser, String bookTitle, Pageable pageable);
}
