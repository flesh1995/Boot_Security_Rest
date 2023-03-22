package ru.kata.spring.boot_security.demo.service;

import javax.validation.Valid;
import ru.kata.spring.boot_security.demo.models.User;


import java.util.List;

public interface ServiceUser {

    List<User> userShow();

    void update(User user);

    void add(@Valid User user);

    void delete(int id);

    User findUser(int id);
    User findUserName(String email);

}
