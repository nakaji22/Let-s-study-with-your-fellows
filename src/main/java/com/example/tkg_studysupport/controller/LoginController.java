package com.example.tkg_studysupport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController{

    @GetMapping("/login")
    public String displayLogin(){
        return "login";
    }
}
