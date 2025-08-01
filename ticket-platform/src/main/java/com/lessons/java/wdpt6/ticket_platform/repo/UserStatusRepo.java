package com.lessons.java.wdpt6.ticket_platform.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.wdpt6.ticket_platform.model.UserStatus;

public interface UserStatusRepo extends JpaRepository<UserStatus, Integer> {
    
    public UserStatus findByName(String name);

}
