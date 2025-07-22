package com.lessons.java.wdpt6.ticket_platform.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    public String tickets(Model model, @RequestParam(required = false) String keyword) {

        List<Ticket> tickets;

        if (keyword != null && !keyword.isEmpty()) {
            tickets = ticketRepo.findByTitleContainingIgnoreCase(keyword);
        } else {
            tickets = ticketRepo.findAll();
        }

        model.addAttribute("tickets", tickets);
        model.addAttribute("keyword", keyword);

        return "tickets/index";

    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("ticket", new Ticket());

        return "tickets/create";

    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "tickets/create";
        } else {
            ticketRepo.save(formTicket);
        }

        return "redirect:/tickets";
    }

    @GetMapping("{id}")
    public String view(Model model, @PathVariable Integer id) {

        Optional<Ticket> ticketOptional = ticketRepo.findById(id);

        if (ticketOptional.isPresent()) {
            model.addAttribute("ticket", ticketOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no ticket found with id: " + id);
        }

        return "tickets/show";

    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {

        Optional<Ticket> ticketOptional = ticketRepo.findById(id);

        if (ticketOptional.isPresent()) {
            model.addAttribute("ticket", ticketOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no ticket found with id: " + id);
        }

        return "tickets/edit";

    }

    @PostMapping("/{id}/edit")
    public String update(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "tickets/edit";
        } else {
            ticketRepo.save(formTicket);
        }

        return "redirect:/tickets";
    }

    @PostMapping("/{id}/delete")
    public String delete(Model model, @PathVariable("id") Integer id){

        Ticket ticketToDelete = ticketRepo.findById(id).get();

        ticketRepo.delete(ticketToDelete);
            
        return "redirect:/tickets";

    }

}
