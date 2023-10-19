package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String login() {
        return  "login";
    }

    @GetMapping("/home")
    public String bla() {
        return "home";
    }

//    @PostMapping("/login")
//    public String in() {
//        return "redirect:/home";
//    }

}
