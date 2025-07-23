package com.lessons.java.wdpt6.ticket_platform.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tickets")
public class Ticket {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   
    //Title
    @NotBlank(message = "title input is mandatory")
    private String title;
        
    //body
    @Lob
    @NotBlank(message = "body input is mandatory")
    private String body;

    // many to one relation
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "you must select one operator")
    @JsonBackReference
    private User user;

    
    
    
    //getters and setters
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getBody() {
        return this.body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    //creation date
    private LocalDate creationDate = LocalDate.now();
    
    public LocalDate getCreationDate() {
        return this.creationDate;
    }
    
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
}
