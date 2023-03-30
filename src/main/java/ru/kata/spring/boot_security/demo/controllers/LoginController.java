package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;
import ru.kata.spring.boot_security.demo.utill.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/form/login")
public class LoginController {
    private final ServiceUser serviceUser;
    private final UserValidator userValidator;

    public LoginController(ServiceUser serviceUser, UserValidator userValidator) {
        this.serviceUser = serviceUser;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String loginPage(@ModelAttribute("user") User user) {
        return "form/login";
    }
//    @GetMapping("/registration")
//    public String newUser(@ModelAttribute("user") User user) {
//        return "form/login";
//    }
    @PostMapping("/registration")
    public String perform(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/form/login";
        }
        serviceUser.registration(user);
        return "redirect:/form/login";
    }
}
