package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;
import ru.kata.spring.boot_security.demo.utill.UserValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {

    private final ServiceUser serviceUser;
    private final UserValidator userValidator;

    public AdminController(ServiceUser serviceUser, UserValidator userValidator) {
        this.serviceUser = serviceUser;
        this.userValidator = userValidator;
    }

    @RequestMapping ("/admin")
    public String snowDbUsers(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userAdmin", serviceUser.findUser(user.getId()));
        model.addAttribute("users", serviceUser.userShow());
        model.addAttribute("userAdd", new User());
        List<Role> roles = serviceUser.roleSet();
        model.addAttribute("allRoles", roles);
        return "show";
    }

    @PostMapping("/admin/add")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/show";}
        serviceUser.add(user);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/edit/{id}")
    public String edit(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "/show";}
        serviceUser.update(id, user);
        return "redirect:/admin";
    }
    @DeleteMapping("/admin/delete/{id}")
    public String delete(@PathVariable("id") int id) {
//        serviceUser.delete(id);
        return "redirect:/admin";
    }
}
