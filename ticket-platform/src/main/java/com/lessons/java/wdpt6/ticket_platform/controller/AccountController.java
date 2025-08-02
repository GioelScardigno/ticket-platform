package com.lessons.java.wdpt6.ticket_platform.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.lessons.java.wdpt6.ticket_platform.model.User;
import com.lessons.java.wdpt6.ticket_platform.model.UserStatus;
import com.lessons.java.wdpt6.ticket_platform.repo.RoleRepo;
import com.lessons.java.wdpt6.ticket_platform.repo.UserRepo;
import com.lessons.java.wdpt6.ticket_platform.repo.UserStatusRepo;
import com.lessons.java.wdpt6.ticket_platform.security.DatabaseUserDetails;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserStatusRepo userStatusRepo;

    @Autowired
    RoleRepo roleRepo;

    @GetMapping
    public String account(Model model, @AuthenticationPrincipal DatabaseUserDetails databaseUserDetails) {

        User user = userRepo.findById(databaseUserDetails.getId()).get();

        model.addAttribute("user", user);

        return "account/index";

    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Integer id,
            @AuthenticationPrincipal DatabaseUserDetails databaseUserDetails) {

        Optional<User> userOptional = userRepo.findById(id);

        if (userOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with id" + id);

        User user = userOptional.get();
        if (!id.equals(databaseUserDetails.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No authorization to view this page");

        List<Ticket> userTickets = user.getTickets();

        boolean isTicketUncompleted = false;

        for (Ticket userTicket : userTickets) {
            if (userTicket.getTicketStatus().getName().equals("TO DO")
                    || userTicket.getTicketStatus().getName().equals("IN  PROGRESS")) {
                isTicketUncompleted = true;
                break;
            }
        }

        List<UserStatus> userStatuses = new ArrayList<UserStatus>();

        if (isTicketUncompleted == true) {
            userStatuses.add(userStatusRepo.findByName("AVAILABLE"));
        } else {
            for (UserStatus userStatus : userStatusRepo.findAll()) {
                userStatuses.add(userStatus);
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("userStatuses", userStatuses);
        model.addAttribute("roles", roleRepo.findAll());

        return "account/edit";

    }

    @PostMapping("/{id}/edit")
    public String update(@Valid @ModelAttribute("user") User formUser, BindingResult bindingResult, Model model) {
        
        User user = userRepo.findById(formUser.getId()).get();

        List<Ticket> userTickets = user.getTickets();

        boolean isTicketUncompleted = false;

        for (Ticket userTicket : userTickets) {
            if (userTicket.getTicketStatus().getName().equals("TO DO")
                    || userTicket.getTicketStatus().getName().equals("IN  PROGRESS")) {
                isTicketUncompleted = true;
                break;
            }
        }

        List<UserStatus> userStatuses = new ArrayList<UserStatus>();

        if (isTicketUncompleted == true) {
            userStatuses.add(userStatusRepo.findByName("AVAILABLE"));
        } else {
            for (UserStatus userStatus : userStatusRepo.findAll()) {
                userStatuses.add(userStatus);
            }
        }

        model.addAttribute("roles", roleRepo.findAll());
        model.addAttribute("userStatuses", userStatuses);

        if (bindingResult.hasErrors()) {
            return "account/edit";
        } else {
            userRepo.save(formUser);
        }

        return "redirect:/account";
    }

}
