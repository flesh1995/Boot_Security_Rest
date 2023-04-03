package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import javax.validation.Valid;
import java.util.List;

public interface ServiceUser {

    List<User> userShow();

    void update(int id, User user);

    void add(@Valid User user);

    void delete(int id);

    User findUser(int id);

    void registration(@Valid User user);

    List<Role> roleSet();
}
