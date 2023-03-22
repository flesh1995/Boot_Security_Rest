package ru.kata.spring.boot_security.demo.DAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;

@Component
@Transactional
public class UserDaoImpl implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> userShow() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }

    @Override
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public User findUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findUserName(String email) {
        return entityManager.find(User.class, email);
    }
}
