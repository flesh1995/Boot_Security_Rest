package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.utill.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class LoginController {
    private final ServiceUser serviceUser;
    private final UserValidator userValidator;

    private final UserDetailsServiceImpl userDetailsService;

    public LoginController(ServiceUser serviceUser, UserValidator userValidator, UserDetailsServiceImpl userDetailsService) {
        this.serviceUser = serviceUser;
        this.userValidator = userValidator;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping ("/login")
    public String loginPage(Model model) {
        model.addAttribute("userAdd", new User());
        return "login";
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        serviceUser.registration(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/authentication")
    public ResponseEntity<User> getUser(Principal principal) {
        User user = userDetailsService.findByUserEmail(principal.getName());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
