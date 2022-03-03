package com.biblioteca.b.service;


import com.biblioteca.b.config.security.TokenService;
import com.biblioteca.b.controller.dto.TokenDto;
import com.biblioteca.b.controller.form.SigninForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class SigninService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    public ResponseEntity<?> authentication(SigninForm signinForm,
                                            UriComponentsBuilder uriComponentsBuilder) {
        UsernamePasswordAuthenticationToken dataSignin = signinForm.converter();
        try {
            Authentication authentication = authenticationManager.authenticate(dataSignin);
            String token = tokenService.generateToken(authentication);
            System.out.println("Seu token: " + token);

            Long id = tokenService.getIdPerson(token);

            URI uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(id).toUri();
            return new ResponseEntity<>(new TokenDto(token, "Bearer"), HttpStatus.valueOf(200));
        } catch (AuthenticationException e) {
            System.out.println("Error: " + e);
            return new ResponseEntity<>("Usuário e/ou senha inválidos", HttpStatus.valueOf(401));
        }
    }
}
