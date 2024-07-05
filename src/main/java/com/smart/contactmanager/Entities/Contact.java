package com.smart.contactmanager.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int contId;
    private String name;
    private String email;
    private String work;
    private String description;
    private String imageUrl;
    private String phone;

    @ManyToOne()
    private User user;

    public int getContId() {
        return contId;
    }
    public void setContId(int contId) {
        this.contId = contId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getWork() {
        return work;
    }
    public void setWork(String work) {
        this.work = work;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public String toString() {
        return "Contact [contId=" + contId + ", name=" + name + ", email=" + email + ", work=" + work + ", description="
                + description + ", imageUrl=" + imageUrl + ", phone=" + phone + ", user=" + user + "]";
    }

    


    
}
