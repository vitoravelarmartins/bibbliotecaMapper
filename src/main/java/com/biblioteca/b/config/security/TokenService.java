package com.biblioteca.b.config.security;

import com.biblioteca.b.model.Person;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${person.jwt.expiration}")
    private String expiration;

    @Value("${person.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication) {
        Person logged = (Person) authentication.getPrincipal();
        Date nowDate = new Date();
        Date dataExpiration = new Date(nowDate.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API Bibblioteca vitor")
                .setSubject(logged.getId().toString())
                .setIssuedAt(nowDate)
                .setExpiration(dataExpiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    public boolean isTokenValidated(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Long getIdPerson(String token) {
        if (token.substring(0,6).equals("Bearer")) {
            String s = token.substring(7);
            Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(s).getBody();
            return Long.parseLong(claims.getSubject());
        }else{
            Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
            return Long.parseLong(claims.getSubject());
        }

    }

}
