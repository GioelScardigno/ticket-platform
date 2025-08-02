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

import com.lessons.java.wdpt6.ticket_platform.model.Category;
import com.lessons.java.wdpt6.ticket_platform.repo.CategoryRepo;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryRepo categoryRepo;
    
    @GetMapping
    public String index(@RequestParam(required = false) String keyword, Model model){

        model.addAttribute("categories", categoryRepo.findAll());

        List<Category> categories;

        if (keyword != null && !keyword.isEmpty()) {
            categories = categoryRepo.findByNameContainingIgnoreCase(keyword);
        } else {
            categories = categoryRepo.findAll();
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("categories", categories);

        return "categories/index";

    }

    @GetMapping("/{id}")
    public String view(Model model, @PathVariable Integer id){

        Optional<Category> categoryOptional = categoryRepo.findById(id);

        if (categoryOptional.isPresent()) {
            model.addAttribute("category", categoryOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no category found with id: " + id);
        }

        return "categories/show";

    }

    @GetMapping("/create")
    public String create(Model model){

        model.addAttribute("category", new Category());

        return "categories/create";

    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute(name = "category") Category formCategory, BindingResult bindingResult, Model model){

        model.addAttribute("category", formCategory);

         if (bindingResult.hasErrors()) {
            return "categories/create";
        } else {
            categoryRepo.save(formCategory);
            return "redirect:/categories";
        }

    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model){

        Optional<Category> categoryOptional = categoryRepo.findById(id);

        if (categoryOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no category with id: " + id);
        model.addAttribute("category", categoryOptional.get());

        return "categories/edit";

    }

    @PostMapping("/{id}/edit")
    public String update(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            return "categories/edit";
        } else {
            categoryRepo.save(formCategory);
            return "redirect:/categories";
        }

    }

    @PostMapping("/{id}/delete")
    public String delete(Model model, @PathVariable Integer id) {

        Category categoryToDelete = categoryRepo.findById(id).get();

        categoryRepo.delete(categoryToDelete);

        return "redirect:/categories";
    }

}
