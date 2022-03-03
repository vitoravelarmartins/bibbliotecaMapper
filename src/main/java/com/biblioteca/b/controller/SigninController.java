package com.biblioteca.b.controller;


import com.biblioteca.b.controller.form.SigninForm;
import com.biblioteca.b.service.SigninService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;


@RestController
@RequestMapping("/signin")
public class SigninController {

    @Autowired
    public SigninService signinService;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody @Valid SigninForm signinForm,
                                  UriComponentsBuilder uriComponentsBuilder) {
        return signinService.authentication(signinForm, uriComponentsBuilder);
    }
}
