package com.example.BD8.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController
{
    @GetMapping("/")
    public String homePage()
    {
        return "home";
    }
}
