package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;

@Controller
public class UserController {
    private final ServiceUser serviceUser;

    public UserController(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }
    @GetMapping(value = "user/user/{id}")
    public String getUserById(@PathVariable("id") int id, Model model) {
        User user = serviceUser.findUser(id);
        model.addAttribute("user", user);
        return "user/user";
    }
}
