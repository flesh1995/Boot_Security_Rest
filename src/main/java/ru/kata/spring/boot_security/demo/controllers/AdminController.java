package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;
import ru.kata.spring.boot_security.demo.utill.UserValidator;

import java.util.List;

@Controller
@RequestMapping ("/admin")
public class AdminController {
    private final ServiceUser serviceUser;
    private final UserValidator userValidator;

    public AdminController(ServiceUser serviceUser, UserValidator userValidator) {
        this.serviceUser = serviceUser;
        this.userValidator = userValidator;
    }
    @GetMapping("")
    public String snowDbUsers(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userAdmin", serviceUser.findUser(user.getId()));
        model.addAttribute("users", serviceUser.userShow());
        model.addAttribute("userAdd", new User());
        List<Role> roles = serviceUser.roleSet();
        model.addAttribute("allRoles", roles);
        return "show";
    }
}
