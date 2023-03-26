package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;


import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ServiceUser serviceUser;

    public AdminController(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }


    @GetMapping()
    public String snowDbUsers(Model model) {
        model.addAttribute("users", serviceUser.userShow());
        return "admin/show";
    }
    @GetMapping("/add")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/add";
    }
    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "admin/add";
        serviceUser.add(user);
        return "redirect:/admin";
    }
    @GetMapping("edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute(serviceUser.findUser(id));
        return "admin/edit";
    }
    @PatchMapping("/{id}")
    public String edit(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "admin/edit";
        serviceUser.update(id, user);
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        serviceUser.delete(id);
        return "redirect:/admin";
    }
}
