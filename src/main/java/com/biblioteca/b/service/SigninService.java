package com.biblioteca.b.service;


import com.biblioteca.b.config.security.TokenService;
import com.biblioteca.b.controller.dto.TokenDto;
import com.biblioteca.b.controller.form.SigninForm;
import org.springframework.beans.factory.annotation.Autowired;
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


    public ResponseEntity<TokenDto> authentication(SigninForm signinForm,
                                                   UriComponentsBuilder uriComponentsBuilder) {
        UsernamePasswordAuthenticationToken dataSignin = signinForm.converter();
        try {
            Authentication authentication = authenticationManager.authenticate(dataSignin);
            String token = tokenService.generateToken(authentication);
            System.out.println("Seu token: "+token);

            Long id = tokenService.getIdPerson(token);


            URI uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(uri).body(new TokenDto(token, "Bearer"));
           // return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        }catch (AuthenticationException e){
            System.out.println("Error: "+e);
            return ResponseEntity.badRequest().build();
        }
    }
}
