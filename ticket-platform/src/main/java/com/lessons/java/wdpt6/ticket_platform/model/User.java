package com.lessons.java.wdpt6.ticket_platform.model;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // username
    @NotBlank(message = "Username must not be empty or have any spaces.")
    private String userName;

    // password
    @NotBlank(message = "Password must not be empty or have any spaces.")
    private String password;

    // many to many relation
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Size(min = 1, message = "You must select at least one role for the user")
    private Set<Role> roles;

    //ticket one to many relation
    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;

    //user status many to one relation
    @ManyToOne
    @JoinColumn(name = "user_status_id", nullable = false)
    @JsonBackReference
    private UserStatus userStatus;
    
    
    
    // getters and setters
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<Role> getRoles() {
        return this.roles;
    }
    
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    public List<Ticket> getTickets() {
        return this.tickets;
    }
    
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    
    public UserStatus getUserStatus() {
        return this.userStatus;
    }
    
    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
    
}
