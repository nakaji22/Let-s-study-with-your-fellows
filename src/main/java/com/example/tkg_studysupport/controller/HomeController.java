package com.example.tkg_studysupport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String displayHome() {
        return "home";
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }
} 