package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.ServiceUser;
import ru.kata.spring.boot_security.demo.utill.UserValidator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping ("/admin")
public class AdminController {

    private final ServiceUser serviceUser;
    private final UserValidator userValidator;

    public AdminController(ServiceUser serviceUser, UserValidator userValidator) {
        this.serviceUser = serviceUser;
        this.userValidator = userValidator;
    }
    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        List<User> allUsers = serviceUser.userShow();
        if (allUsers != null && !allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        serviceUser.add(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int id) {
        return serviceUser.findUser(id);
    }
    @PatchMapping("/edit/{id}")
    public ResponseEntity<User> edit(@Valid @RequestBody User user, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);}
        serviceUser.update(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        serviceUser.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
