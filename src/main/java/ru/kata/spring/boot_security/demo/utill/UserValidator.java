package ru.kata.spring.boot_security.demo.utill;


import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserValidator implements Validator {
    protected Map<Integer, User> dateBase = new HashMap<Integer, User>();

    private final UserRepository userRepository;

    private final UserDetailsServiceImpl userDetailsService;

    public UserValidator(UserRepository userRepository, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userRepository.findByUserEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "", "Данное наимнование почты уже занято");
        }
    }
}
