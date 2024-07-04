package com.smart.contactmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.smart.contactmanager.Entities.User;
import com.smart.contactmanager.Repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // fetch the user from the database

       User user = userRepository.getUserByUserName(username);
       if(user != null){
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
       }

           throw new UsernameNotFoundException("could not found user");
    //    if (user == null) {
    //    }

    //    CustomUserDetails customUserDetails = new CustomUserDetails(user);
    //    return customUserDetails;
    }
    
}
