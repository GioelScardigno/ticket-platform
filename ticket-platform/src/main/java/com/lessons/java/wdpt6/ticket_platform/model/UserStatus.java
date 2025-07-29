package com.lessons.java.wdpt6.ticket_platform.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "user_statuses")
public class UserStatus {
    
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //name
    @NotBlank
    private String name;

    //user one to many relation
    @OneToMany(mappedBy = "userStatus")
    private List<User> users;

    
    
    //getters and setters
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<User> getUsers() {
        return this.users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
}
