package com.biblioteca.b.controller.dto;

import com.biblioteca.b.model.Book;
import com.biblioteca.b.model.StatusBook;
import lombok.Getter;


@Getter
public class BookDto {

    private Integer amount;
    private String title;
    private String author;
    private StatusBook status;

    public BookDto(Book book) {
        amount = book.getAmount();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.status = book.getStatus();
    }
}
