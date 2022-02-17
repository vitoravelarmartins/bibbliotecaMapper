package com.biblioteca.b.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Objects;


@Getter
@Setter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBook;

    private Integer amount;
    private String title;
    private String author;

    @Enumerated(EnumType.STRING)
    private StatusBook statusBook = StatusBook.DISPONIVEL;

    public Book() {
    }

    public Book(Integer amount, String title, String author) {
        this.amount = amount;
        this.title = title;
        this.author = author;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return idBook.equals(book.idBook) && amount.equals(book.amount) && title.equals(book.title) && author.equals(book.author) && statusBook.equals(book.statusBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBook, amount, title, author, statusBook);
    }
}
