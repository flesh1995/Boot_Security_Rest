package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;

@Controller
@RequestMapping("/user")
public class UserRestController {
    private final ServiceUser serviceUser;

    public UserRestController(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }
    @GetMapping()
    public String showInfoUser(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", serviceUser.findUser(user.getId()));
        return "user";
    }
}
