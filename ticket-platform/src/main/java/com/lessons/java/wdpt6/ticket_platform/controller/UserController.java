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

import com.lessons.java.wdpt6.ticket_platform.model.User;
import com.lessons.java.wdpt6.ticket_platform.repo.RoleRepo;
import com.lessons.java.wdpt6.ticket_platform.repo.UserRepo;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;
    
    @GetMapping
    public String index(@RequestParam(required = false) String keyword ,Model model){

        List<User> users;

        if (keyword != null && !keyword.isEmpty()) {
            users = userRepo.findByUserNameContainingIgnoreCase(keyword);
        } else {
            users = userRepo.findAll();
        }

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);

        return "users/index";

    }

    @GetMapping("{id}")
    public String view(Model model, @PathVariable Integer id) {

        Optional<User> userOptional = userRepo.findById(id);

        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no user found with id: " + id);
        }

        return "users/show";
    }

    @GetMapping("/create")
    public String create(Model model){

        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepo.findAll());

        return "users/create";

    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("user") User formUser, BindingResult bindingResult, Model model){

        model.addAttribute("roles", roleRepo.findAll());

        if (bindingResult.hasErrors()) {
            return "users/create";
        } else {
            userRepo.save(formUser);
        }

        return "redirect:/users";

    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {

        model.addAttribute("roles", roleRepo.findAll());

        Optional<User> userOptional = userRepo.findById(id);

        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no user found with id: " + id);
        }

        return "users/edit";
    }

    @PostMapping("/{id}/edit")
    public String update(@Valid @ModelAttribute("user") User formUser, BindingResult bindingResult, Model model) {

        model.addAttribute("roles", roleRepo.findAll());

        if (bindingResult.hasErrors()) {
            return "users/edit";
        } else {
            userRepo.save(formUser);
        }

        return "redirect:/users";
    }

    @PostMapping("/{id}/delete")
    public String delete(Model model, @PathVariable Integer id){

        User userToDelete = userRepo.findById(id).get();

        userRepo.delete(userToDelete);
            
        return "redirect:/users";

    }

}
