package com.biblioteca.b.controller.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SigninForm {

    @NotNull @NotEmpty @Email
    private String email;
    @NotNull @NotEmpty @Size(min = 8)
    private String passwordKey;

    public UsernamePasswordAuthenticationToken converter(){
        return new UsernamePasswordAuthenticationToken(email, passwordKey);
    }
}
