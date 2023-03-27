package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user").setViewName("user");
    }
}


/*
insert into users (age, email, lastname, password, name) VALUES (27, 'master@mail.ru', 'Корчагин', '$2a$12$Ti9N4gwU2stt3vP1SOltBuJ3wq3eC1kPhALtYQqok7.y.Rffx67YS', 'Стив');
insert into roles (name_role) value ('ROLE_ADMIN');
insert into user_roles (user_id, role_id) VALUES (1, 1);
insert into roles (name_role) value ('ROLE_USER');
* */