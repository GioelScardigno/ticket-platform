package com.lessons.java.wdpt6.ticket_platform.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tickets")
public class Ticket {
    
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    //Title
    @NotBlank(message = "title input is mandatory")
    @Size(max = 30, message = "The title exceeds the 30 characters limit")
    private String title;
    
    //body
    @Lob
    @NotBlank(message = "body input is mandatory")
    private String body;

    //creation date
    private LocalDate creationDate = LocalDate.now();
    
    //user many to one relation
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "you must select one operator")
    @JsonBackReference
    private User user;

    //status many to one relation
    @ManyToOne
    @JoinColumn(name = "ticket_status_id", nullable = false)
    @JsonBackReference
    private TicketStatus ticketStatus;

    //ticket note one to one relation
    @OneToMany(mappedBy = "ticket")
    private List<Note> notes;

    
    
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
    
    public LocalDate getCreationDate() {
        return this.creationDate;
    }
    
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public TicketStatus getTicketStatus() {
        return this.ticketStatus;
    }
    
    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
    
    public List<Note> getNotes() {
        return this.notes;
    }
    
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

}
