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

import com.lessons.java.wdpt6.ticket_platform.model.Note;
import com.lessons.java.wdpt6.ticket_platform.repo.NoteRepo;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    NoteRepo noteRepo;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute(name = "note") Note formNote, BindingResult bindingResult, Model model) {

        model.addAttribute("note", formNote);

        if (bindingResult.hasErrors()) {
            return "notes/create";
        } else {
            noteRepo.save(formNote);
            return "redirect:/tickets";
        }

    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {

        Optional<Note> noteOptional = noteRepo.findById(id);

        if (noteOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no note with id: " + id);
        model.addAttribute("note", noteOptional.get());

        return "notes/edit";

    }

    @PostMapping("/{id}/edit")
    public String update(@Valid @ModelAttribute("note") Note formNote, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "tickets/edit";
        } else {
            noteRepo.save(formNote);
            return "redirect:/tickets";
        }

    }

    @PostMapping("/{id}/delete")
    public String delete(Model model, @PathVariable Integer id) {

        Note noteToDelete = noteRepo.findById(id).get();

        noteRepo.delete(noteToDelete);

        return "redirect:/tickets";
    }

}
