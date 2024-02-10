package com.compony.finalproject.controllers;

import com.compony.finalproject.model.Accommodation;
import com.compony.finalproject.service.impl.AccommodationServiceImpl;
import com.compony.finalproject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/accommodations")
public class AccommodationController {

    private final AccommodationServiceImpl accommodationService;
    private final UserServiceImpl userService;


    @Autowired
    public AccommodationController(AccommodationServiceImpl accommodationService, UserServiceImpl userService) {
        this.accommodationService = accommodationService;
        this.userService = userService;
    }

    @GetMapping
    public String showAccommodations(Model model) {
        List<Accommodation> accommodation = accommodationService.getAll();
        model.addAttribute("accommodation", accommodation);
        return "accommodations";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("accommodation", new Accommodation());
        return "createAccommodation";
    }

    @PostMapping("/create")
    public String createAccommodation(@ModelAttribute Accommodation accommodation,
                                      @RequestParam(name = "username") String username) {
        Long userId = userService.getUserIdByUsername(username);
        accommodationService.create(accommodation, userId);
        return "redirect:/accommodations";
    }

    @GetMapping("/edit/{id}")
    public String showEditAccommodation(@PathVariable Long id, Model model) {
        Accommodation accommodation = accommodationService.getById(id);
        model.addAttribute("accommodation", accommodation);
        return "editAccommodation";
    }

    @PostMapping("/edit/{id}")
    public String editAccommodation(@PathVariable Long id, @ModelAttribute Accommodation accommodation) {
        accommodation.setId(id);
        accommodationService.create(accommodation);
        return "redirect:/accommodations";
    }

    @GetMapping("/filter")
    public String filterAccommodations(
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam(name = "price", required = false) String price,
            Model model) {
        List<Accommodation> filteredAccommodations = accommodationService.filterAccommodation(city, country, price);
        model.addAttribute("accommodations", filteredAccommodations);
        return "filteredAccommodations";
    }

    @GetMapping("/booking/{id}")
    public String showBookingForm(@PathVariable Long id, Model model) {
        Accommodation accommodation = accommodationService.getById(id);
        model.addAttribute("accommodation", accommodation);
        return "toBook";
    }

    @PostMapping("/booking/{id}")
    public String toBookAccommodation(
            @PathVariable Long id,
            @RequestParam(name = "availableFrom") LocalDate availableFrom,
            @RequestParam(name = "availableTo") LocalDate availableTo,
            @RequestParam(name = "username") String username,
            Model model) {
        Long userId = userService.getUserIdByUsername(username);
        accommodationService.book(availableFrom, availableTo, id, userId);
        model.addAttribute("message", "Размещение успешно забронировано!");
        return "redirect:/accommodations";
    }

    @GetMapping("/setRating/{id}")
    public String showRatingForm(@PathVariable Long id, Model model) {
        Accommodation accommodation = accommodationService.getById(id);
        model.addAttribute("accommodation", accommodation);
        return "giveRatingAccommodation";
    }

    @PostMapping("/setRating/{id}")
    public String setRating(@PathVariable Long id, @RequestParam Double rating, Model model) {
        double newAverageRating = accommodationService.addRatingAndUpdateAverage(id, rating);
        model.addAttribute("accommodation", newAverageRating);
        return "redirect:/accommodations";
    }



}
