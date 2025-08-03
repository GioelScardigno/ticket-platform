package com.lessons.java.wdpt6.ticket_platform.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lessons.java.wdpt6.ticket_platform.model.Ticket;
import com.lessons.java.wdpt6.ticket_platform.repo.TicketRepo;

@RestController
@RequestMapping("/api/tickets")
public class TicketRestController {

    @Autowired
    TicketRepo ticketRepo;

    @GetMapping
    public ResponseEntity<List<Ticket>> index(@RequestParam(required = false) String categoryFilter, @RequestParam(required = false) String statusFilter){

        List<Ticket> tickets = new ArrayList<Ticket>();
        
        if (categoryFilter != null && !categoryFilter.isEmpty() && !categoryFilter.isBlank() && statusFilter != null && !statusFilter.isEmpty() && !statusFilter.isBlank()) {
           for (Ticket filteredTicket : ticketRepo.findByCategoryName(categoryFilter)) {
                if (filteredTicket.getTicketStatus().getName().equals(statusFilter)) {
                    tickets.add(filteredTicket);
                }
           }
        } else if (statusFilter != null && !statusFilter.isEmpty() && !statusFilter.isBlank()) {
            tickets = ticketRepo.findByTicketStatusName(statusFilter);
        } else if (categoryFilter != null && !categoryFilter.isEmpty() && !categoryFilter.isBlank()) {
            tickets = ticketRepo.findByTicketStatusName(categoryFilter);
        } else {
            tickets = ticketRepo.findAll();
        }

        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
    }

}
