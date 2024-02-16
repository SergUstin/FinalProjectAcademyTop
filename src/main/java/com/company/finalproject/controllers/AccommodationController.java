package com.company.finalproject.controllers;

import com.company.finalproject.model.Accommodation;
import com.company.finalproject.service.impl.AccommodationServiceImpl;
import com.company.finalproject.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
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
        log.info("Showing accommodations");
        List<Accommodation> accommodation = accommodationService.getAll();
        model.addAttribute("accommodation", accommodation);
        return "accommodations";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        log.info("Creating accommodation");
        model.addAttribute("accommodation", new Accommodation());
        return "createAccommodation";
    }
    @PostMapping("/create")
    public String createAccommodation(@ModelAttribute Accommodation accommodation,
                                      @RequestParam(name = "username") String username) {
        Long userId = userService.getUserIdByUsername(username);
        log.info("Showing edit form of accommodation with ID: {}", userId);
        accommodationService.createAndAddLandlordId(accommodation, userId);
        return "redirect:/accommodations";
    }
    @GetMapping("/edit/{id}")
    public String showEditAccommodation(@PathVariable Long id, Model model) {
        log.info("Editing accommodation with ID: {}", id);
        Accommodation accommodation = accommodationService.getById(id);
        model.addAttribute("accommodation", accommodation);
        return "editAccommodation";
    }
    @PostMapping("/edit/{id}")
    public String editAccommodation(@PathVariable Long id, @ModelAttribute Accommodation accommodation) {
        log.info("Editing accommodation with id: {}", id);
        accommodationService.edit(id, accommodation);
        return "redirect:/accommodations";
    }
    @GetMapping("/filter")
    public String filterAccommodations(
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam(name = "price", required = false) String price,
            Model model) {
        log.info("Filtering accommodations with city: {}, country: {}, price: {}", city, country, price);
        List<Accommodation> filteredAccommodations = accommodationService.filterAccommodation(city, country, price);
        model.addAttribute("accommodations", filteredAccommodations);
        return "filteredAccommodations";
    }
    @GetMapping("/booking/{id}")
    public String showBookingForm(@PathVariable Long id, Model model) {
        log.info("Showing booking form for accommodation with id: {}", id);
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
        log.info("Booking accommodation with id: {}, availableFrom: {}, availableTo: {}, username: {}", id, availableFrom, availableTo, username);
        Long userId = userService.getUserIdByUsername(username);
        accommodationService.book(availableFrom, availableTo, id, userId);
        model.addAttribute("message", "Размещение успешно забронировано!");
        return "redirect:/accommodations";
    }
    @GetMapping("/setRating/{id}")
    public String showRatingForm(@PathVariable Long id, Model model) {
        log.info("Showing rating form for accommodation with id: {}", id);
        Accommodation accommodation = accommodationService.getById(id);
        model.addAttribute("accommodation", accommodation);
        return "giveRatingAccommodation";
    }
    @PostMapping("/setRating/{id}")
    public String setRating(@PathVariable Long id, @RequestParam Integer rating, Model model) {
        log.info("Setting rating {} for accommodation with id: {}", rating, id);
        Integer newAverageRating = accommodationService.addRatingAndUpdateAverage(id, rating);
        model.addAttribute("accommodation", newAverageRating);
        return "redirect:/accommodations";
    }
}
