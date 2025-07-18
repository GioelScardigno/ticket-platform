package com.lessons.java.wdpt6.ticket_platform.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.lessons.java.wdpt6.ticket_platform.model.Ticket;
import com.lessons.java.wdpt6.ticket_platform.repo.TicketRepo;

import jakarta.validation.Valid;


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

    @GetMapping("/create")
    public String create(Model model){

        model.addAttribute("ticket", new Ticket());

        return "tickets/create";

    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
                        return "tickets/create";
        } else {
            ticketRepo.save(formTicket);
        }

        return "redirect:/tickets";
    }



    @GetMapping("{id}")
    public String view(Model model, @PathVariable Integer id){

        Optional<Ticket> ticketOptional = ticketRepo.findById(id);

        if (ticketOptional.isPresent()) {
                        model.addAttribute("ticket", ticketOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no ticket found with id: " + id);
        }

        return "tickets/show";

    }

}
