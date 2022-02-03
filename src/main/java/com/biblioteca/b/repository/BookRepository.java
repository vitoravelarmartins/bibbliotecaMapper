package com.biblioteca.b.repository;

import com.biblioteca.b.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
