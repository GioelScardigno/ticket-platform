package com.lessons.java.wdpt6.ticket_platform.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.wdpt6.ticket_platform.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    
    public Role findByTitle(String title);

}
