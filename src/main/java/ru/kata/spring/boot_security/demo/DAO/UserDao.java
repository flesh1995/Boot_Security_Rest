package ru.kata.spring.boot_security.demo.DAO;

import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

public interface UserDao {
    List<User> userShow();

    void update(User user);

    void add(User user);

    void delete(int id);

    User findUser(int id);

    User findUserName(String email);
}
