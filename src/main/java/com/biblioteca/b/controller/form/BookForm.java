package com.biblioteca.b.controller.form;

import com.biblioteca.b.model.Book;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class BookForm {

    @NotNull @Positive
    private Integer amount;
    @NotNull @NotEmpty @Length(min = 2, max = 100)
    private String title;
    @NotNull @NotEmpty @Length(min = 2, max = 100)
    private String author;

}
