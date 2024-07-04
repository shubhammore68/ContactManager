package com.smart.contactmanager.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.contactmanager.Entities.User;
import com.smart.contactmanager.Repository.UserRepository;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void commonData(Model model, Principal principal){
        String username = principal.getName();
        User user = userRepository.getUserByUserName(username);

        model.addAttribute("user", user);
    }
    
    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal){

       
        
        return "normal/user_dashboard";
    }

    @GetMapping("/add-contact")
    public String addContact() {
        
        return "normal/addcontact";
    }
    
}
