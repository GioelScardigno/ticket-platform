package com.lessons.java.wdpt6.ticket_platform.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.wdpt6.ticket_platform.model.TicketStatus;

public interface TicketStatusRepo extends JpaRepository<TicketStatus, Integer> {
    public TicketStatus findByName(String name);
}
