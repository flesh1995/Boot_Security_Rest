package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;
import ru.kata.spring.boot_security.demo.utill.UserValidator;


import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ServiceUser serviceUser;
    private final UserValidator userValidator;

    public AdminController(ServiceUser serviceUser, UserValidator userValidator) {
        this.serviceUser = serviceUser;
        this.userValidator = userValidator;
    }


    @GetMapping()
    public String snowDbUsers(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userAdmin", serviceUser.findUser(user.getId()));
        model.addAttribute("users", serviceUser.userShow());
        model.addAttribute("userAdd", new User());
        return "admin/show";
    }
//    @GetMapping("/add")
//    public String newUser(@ModelAttribute("user") User user) {
//        return "admin/add";
//    }
    @PostMapping("/add")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "admin/show";
        serviceUser.add(user);
        return "redirect:/admin";
    }
//    @GetMapping("edit/{id}")
//    public String editUser(@PathVariable("id") int id, Model model) {
//        model.addAttribute(serviceUser.findUser(id));
//        return "admin/show";
//    }
    @PatchMapping("edit/{id}")
    public String edit(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "admin/show";
        serviceUser.update(id, user);
        return "redirect:/admin";
    }
    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable("id") int id) {
        serviceUser.delete(id);
        return "redirect:/admin";
    }
}
