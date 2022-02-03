package com.biblioteca.b.model;

import com.biblioteca.b.controller.dto.BookDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount;
    private String title;
    private String author;

    @Enumerated(EnumType.STRING)
    private StatusBook status = StatusBook.DISPONIVEL;

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
        return id.equals(book.id) && amount.equals(book.amount) && title.equals(book.title) && author.equals(book.author) && status.equals(book.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, title, author, status);
    }

    public static List<BookDto> convert(List<Book> bookList) {
        return bookList.stream().map(BookDto::new).collect(Collectors.toList());
    }
}
