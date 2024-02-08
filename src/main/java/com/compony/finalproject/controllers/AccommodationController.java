package com.compony.finalproject.controllers;

import com.compony.finalproject.model.Accommodation;
import com.compony.finalproject.service.impl.AccommodationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/accommodations")
public class AccommodationController {

    private final AccommodationServiceImpl service;

    @Autowired
    public AccommodationController(AccommodationServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public String showAccommodations(Model model) {
        List<Accommodation> accommodation = service.getAll();
        model.addAttribute("accommodation", accommodation);
        return "accommodations";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("accommodation", new Accommodation());
        return "createAccommodation";
    }

    @PostMapping("/create")
    public String createAccommodation(@ModelAttribute Accommodation accommodation) {
        service.create(accommodation);
        return "redirect:/accommodations";
    }

    @GetMapping("/edit/{id}")
    public String showEditAccommodation(@PathVariable Long id, Model model) {
        Accommodation accommodation = service.getById(id);
        model.addAttribute("accommodation", accommodation);
        return "editAccommodation";
    }

    @PostMapping("/edit/{id}")
    public String editAccommodation(@PathVariable Long id, @ModelAttribute Accommodation accommodation) {
        accommodation.setId(id);
        service.create(accommodation);
        return "redirect:/accommodations";
    }

    @GetMapping("/filter")
    public String filterAccommodations(
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam(name = "price", required = false) String price,
            Model model) {
        List<Accommodation> filteredAccommodations = service.filterAccommodation(city, country, price);
        model.addAttribute("accommodations", filteredAccommodations);
        return "filteredAccommodations";
    }
}
