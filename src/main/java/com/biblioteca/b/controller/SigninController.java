package com.biblioteca.b.controller;

import com.biblioteca.b.controller.dto.TokenDto;
import com.biblioteca.b.controller.form.SigninForm;
import com.biblioteca.b.service.SigninService;
import org.hibernate.validator.internal.metadata.aggregated.rule.OverridingMethodMustNotAlterParameterConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@Controller
@RequestMapping("/signin")
public class SigninController {

    @Autowired
    public SigninService signinService;

    @PostMapping
    public ResponseEntity<?> auth(
                                         @RequestBody @Valid SigninForm signinForm,
                                         UriComponentsBuilder uriComponentsBuilder){
        return signinService.authentication(signinForm, uriComponentsBuilder);
    }
}
