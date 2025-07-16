package com.lessons.java.wdpt6.ticket_platform.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lessons.java.wdpt6.ticket_platform.model.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {

}
