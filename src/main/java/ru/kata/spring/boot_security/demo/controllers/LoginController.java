package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.DispatcherServlet;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;
import ru.kata.spring.boot_security.demo.service.ServiceUserImpl;
import ru.kata.spring.boot_security.demo.utill.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/form")
public class LoginController {
    private final ServiceUser serviceUser;
    private final UserValidator userValidator;

    public LoginController(ServiceUser serviceUser, UserValidator userValidator) {
        this.serviceUser = serviceUser;
        this.userValidator = userValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "form/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "form/registration";
    }

    @PostMapping("/registration")
    public String perform(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/form/registration";
        }

        serviceUser.registration(user);

        return "redirect:form/login";
    }
}
