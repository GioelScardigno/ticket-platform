package com.lessons.java.wdpt6.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lessons.java.wdpt6.ticket_platform.repo.TicketRepo;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketRepo ticketRepo;

    @GetMapping
    public String tickets(Model model){

        model.addAttribute("tickets", ticketRepo.findAll());

        return "tickets/index";

    }

}
