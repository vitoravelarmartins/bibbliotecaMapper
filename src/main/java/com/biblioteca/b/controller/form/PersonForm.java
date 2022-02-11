package com.biblioteca.b.controller.form;


import com.biblioteca.b.model.Person;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PersonForm {

    @NotNull
    @NotEmpty
    @Length(min = 2, max = 100)
    private String firstName;
    @NotNull @NotEmpty @Length(min = 2, max = 100)
    private String lastName;
    @NotNull @NotEmpty @Length(min = 7) @Email
    private String email;
    @NotNull @NotEmpty @Length(min = 8)
    private String passwordKey;


    public Person convert(){
        String passwordKeyCrypt = new BCryptPasswordEncoder().encode(passwordKey);
        return new Person(firstName,lastName,email,passwordKeyCrypt);
    }
}
