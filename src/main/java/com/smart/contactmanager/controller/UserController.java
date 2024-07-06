package com.smart.contactmanager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.contactmanager.Entities.Contact;
import com.smart.contactmanager.Entities.User;
import com.smart.contactmanager.Repository.ContactRepository;
import com.smart.contactmanager.Repository.UserRepository;
import com.smart.contactmanager.config.MessageConfig;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;





@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

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
    public String processContact(@ModelAttribute Contact contact, 
        Model model,
        @RequestParam("profileImg") MultipartFile file, 
        Principal principal,
        HttpSession session) {
        
        String username = principal.getName();
        User user = this.userRepository.getUserByUserName(username);
        contact.setUser(user);
        
        if (file.isEmpty()) {
            System.out.println("file is empty");
            contact.setImageUrl("contact.png");
            
        }else{
           try {
            contact.setImageUrl(file.getOriginalFilename());

            File saveFile = new ClassPathResource("static/images").getFile();
            
            Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            
            session.setAttribute("message", new MessageConfig("Contact added successfully", "success"));
            // session.removeAttribute("message");
            
        } catch (Exception e) {
            session.setAttribute("message", new MessageConfig("Something went wrong", "danger"));
        }
        
        }
        user.getContacts().add(contact);
        this.userRepository.save(user);

        // session.removeAttribute("message");

        // System.out.println(contact);
        return "normal/addcontact";
    }
    

    // get all the contacts

    @GetMapping("/allcontacts/{page}")
    public String getMethodName( @PathVariable("page") Integer page,Model model, Principal principal) {
        
        String username = principal.getName();
        User user = this.userRepository.getUserByUserName(username);
        Pageable pageable = PageRequest.of(page, 5);
        Page<Contact> contacts = this.contactRepository.findContactByUser(user.getId(), pageable);
        model.addAttribute("contacts", contacts);
        model.addAttribute("currentpage", page);
        model.addAttribute("totalpages", contacts.getTotalPages());
        return "normal/allcontacts";
    }


    // view details of contact

    @GetMapping("/contact/{cid}")
    public String getMethodName(@PathVariable("cid") int cid, Model model, Principal principal) {

       Optional<Contact> contactoOptional = this.contactRepository.findById(cid);
       Contact contact = contactoOptional.get();

       String username = principal.getName();
       User user = this.userRepository.getUserByUserName(username);

       if(user.getId() == contact.getUser().getId()){
           model.addAttribute("contact", contact);
       }
        
        return "normal/contactdetail";
    }
    

    // delete contact
   @GetMapping("/delete/{cid}")
   public String deleteContact(@PathVariable("cid") int cid, Principal principal) {

    Optional<Contact> contactoOptional = this.contactRepository.findById(cid);
       Contact contact = contactoOptional.get();

       String username = principal.getName();
       User user = this.userRepository.getUserByUserName(username);

       if(user.getId() == contact.getUser().getId()){ 
           this.contactRepository.deleteById(cid);
       }

        return "redirect:/user/allcontacts/0";
   }
   
    
    
}
