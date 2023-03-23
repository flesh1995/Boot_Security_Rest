package ru.kata.spring.boot_security.demo.utill;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;

@Component
public class UserValidator implements Validator {

    private final UserDetailsServiceImpl userDetailsService;

    public UserValidator(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;

        if (userDetailsService.loadUserByUsername(user.getEmail()) != null) {
           errors.rejectValue("username", "", "Пользователь с такой почтой уже зарегистрирован");
        }

    }
}
