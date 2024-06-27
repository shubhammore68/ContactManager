package com.smart.contactmanager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.contactmanager.Entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
}
