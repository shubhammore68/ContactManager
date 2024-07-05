package com.smart.contactmanager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.contactmanager.Entities.Contact;
import com.smart.contactmanager.Entities.User;
import com.smart.contactmanager.Repository.UserRepository;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
    public String addContact(Model model) {
        model.addAttribute("contact", new Contact());
        
        return "normal/addcontact";
    }
    

    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImg") MultipartFile file, Principal principal) {
        
        String username = principal.getName();
        User user = this.userRepository.getUserByUserName(username);
        contact.setUser(user);
        
        if (file.isEmpty()) {
            System.out.println("file is empty");
            
        }else{
           try {
            contact.setImageUrl(file.getOriginalFilename());

            File saveFile = new ClassPathResource("static/images").getFile();
            
            Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

           } catch (Exception e) {
            
           }

        }
        user.getContacts().add(contact);
        this.userRepository.save(user);

        // System.out.println(contact);
        return "normal/addcontact";
    }
    
}
