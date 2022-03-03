package com.biblioteca.b.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;


@Getter
@Setter
public class RentedForm {

    @NotNull @Positive
    private Long book;
    @NotNull @FutureOrPresent
    private LocalDateTime dateDelivery;

}
