package com.lessons.java.wdpt6.ticket_platform.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.wdpt6.ticket_platform.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String userName);
    
    public List<User> findByUserNameContainingIgnoreCase(String title);

    public List<User> findByRolesTitle(String title);

}
