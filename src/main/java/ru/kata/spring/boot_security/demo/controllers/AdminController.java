package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    @RequestMapping ("/admin")
    public String snowDbUsers() {
        return "show";
    }
}
