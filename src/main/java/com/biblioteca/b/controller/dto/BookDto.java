package com.biblioteca.b.controller.dto;


import com.biblioteca.b.model.StatusBook;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookDto {

    private Long idBook;
    private Integer amount;
    private String title;
    private String author;
    private StatusBook statusBook;

}
