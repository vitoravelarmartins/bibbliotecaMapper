package com.biblioteca.b.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SecurityCheck {

    public boolean check(Long idUser, Authentication authentication) {
        Long principal = (Long) authentication.getPrincipal();
        return principal.equals(idUser);
    }
}
