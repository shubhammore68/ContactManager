package com.smart.contactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.smart.contactmanager.Entities.User;
import com.smart.contactmanager.Repository.UserRepository;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {
    
    // @Autowired
    // private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model){

        model.addAttribute("title", "Home - Smart Contact Manager");
       
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About - Smart Contact Manager");
        return "about";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Register - Smart Contact Manager");
        return "signup";
    }
    
}
