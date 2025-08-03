package com.lessons.java.wdpt6.ticket_platform.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.wdpt6.ticket_platform.model.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {

    public List<Ticket> findByTitleContainingIgnoreCase(String title);

    public List<Ticket> findByCategoryName(String name);

    public List<Ticket> findByTicketStatusName(String name);
    
}
