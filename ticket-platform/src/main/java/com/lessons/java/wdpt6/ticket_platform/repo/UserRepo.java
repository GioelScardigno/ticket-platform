package com.lessons.java.wdpt6.ticket_platform.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.wdpt6.ticket_platform.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    
    public List<User> findByUserNameContainingIgnoreCase(String title);

}
