package com.smart.contactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.smart.contactmanager.Entities.User;
import com.smart.contactmanager.Repository.UserRepository;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;





@Controller
public class HomeController {
    
    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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


    
    public String aboutpage(){
        return "home";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Register - Smart Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/do_register")
        public String postMethodName(@Valid @ModelAttribute("user") User user,
        Model model, BindingResult br, 
        @RequestParam(value = "aggrement", defaultValue= "false") boolean aggrement, 
        HttpSession session){
        

        try {
            if (!aggrement) {
                System.out.println("you have not agreed term and conditions");
            }

            if (br.hasErrors()) {
                System.out.println(br);
                model.addAttribute("user", user);
                return "signup";
            }
    
            user.setRole("USER");
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            this.userRepository.save(user);
            session.setAttribute("message", "Successfully Registered!!");
            session.setAttribute("type", "alert-success");
            // session.setAttribute("message", new MessageConfig("User created successfully", "alert-success"));

            return "signup";
        } catch (Exception e) {
           e.printStackTrace();
            session.setAttribute("message", "something went wrong");
            session.setAttribute("type", "alert-danger");
            // session.setAttribute("message", new MessageConfig("something went wrong", "alert-danger"));
            return "signup";
        }

    }
    
    
}
