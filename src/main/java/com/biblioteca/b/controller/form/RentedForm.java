package com.biblioteca.b.controller.form;



import com.biblioteca.b.model.Book;
import com.biblioteca.b.model.Person;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
public class RentedForm {

    private Long person;
    @NotNull @Positive
    private Long book;
    @NotNull @FutureOrPresent
    private LocalDateTime dateDelivery;

}
