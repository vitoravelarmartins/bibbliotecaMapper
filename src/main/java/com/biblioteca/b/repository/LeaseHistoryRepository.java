package com.biblioteca.b.repository;


import com.biblioteca.b.model.LeaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaseHistoryRepository extends JpaRepository<LeaseHistory,Long> {
}
