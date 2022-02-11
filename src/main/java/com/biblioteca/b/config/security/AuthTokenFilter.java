package com.biblioteca.b.config.security;

import com.biblioteca.b.model.Person;
import com.biblioteca.b.repository.PersonRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private PersonRepository personRepository;

    public AuthTokenFilter(TokenService tokenService, PersonRepository personRepository) {
        this.tokenService = tokenService;
        this.personRepository = personRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);
              boolean tokenState = tokenService.isTokenValidated(token);
        if (tokenState == true){
            authenticatePerson(token);
        }
        filterChain.doFilter(request,response);

    }

    private void authenticatePerson(String token) {
        Long idPerson = tokenService.getIdPerson(token);
        Person person = personRepository.findById(idPerson).get();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(person.getId(),
                null, person.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }


    private String recoverToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(token == null || token.isEmpty()|| !token.startsWith("Bearer ")){
            return null;
        }else
            return token.substring(7, token.length());
    }
}
