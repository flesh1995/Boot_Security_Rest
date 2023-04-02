package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ServiceUserImpl implements ServiceUser {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public ServiceUserImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public List<User> userShow() {
        return userRepository.findAll();
    }

    @Override
    public User findUser(int id) {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.orElse(null);
    }

    @Override
    @Transactional
    public void registration(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRoles() == null) {
            user.setRoles(userRepository.getById(user.getId()).getRoles());
        }else {
            List<Role> roles = user.getRoles().stream().map(role -> roleRepository.findByRole(role.getName()).get())
                    .collect(Collectors.toList());
            user.setRoles(roles);
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(int id, @Valid User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(id);
        List<Role> roles = user.getRoles().stream().map(role -> roleRepository.findByRole(role.getName()).get())
                    .collect(Collectors.toList());
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRoles() == null) {
            user.setRoles(userRepository.getById(user.getId()).getRoles());
        }else {
            List<Role> roles = user.getRoles().stream().map(role -> roleRepository.findByRole(role.getName()).get())
                    .collect(Collectors.toList());
            user.setRoles(roles);
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }
    @Override
    public List<Role> roleSet() {
        return roleRepository.findAll();
    }

}
