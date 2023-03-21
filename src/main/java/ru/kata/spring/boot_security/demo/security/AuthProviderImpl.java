package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthProviderImpl implements AuthenticationProvider {

    private final
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        UserDetails userDetails =
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
