package com.biblioteca.b.repository;

import com.biblioteca.b.model.LeaseHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaseHistoryRepository extends JpaRepository<LeaseHistory, Long> {
    Page<LeaseHistory> findByPerson_IdPerson(Long idUser, Pageable pageable);

    Page<LeaseHistory> findByPerson_IdPersonAndBook_Title(Long idUser, String bookTitle, Pageable pageable);
}
