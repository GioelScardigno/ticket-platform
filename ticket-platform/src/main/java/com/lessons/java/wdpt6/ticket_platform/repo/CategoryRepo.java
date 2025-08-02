package com.lessons.java.wdpt6.ticket_platform.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.wdpt6.ticket_platform.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    
    public List<Category> findByNameContainingIgnoreCase(String name);

}
