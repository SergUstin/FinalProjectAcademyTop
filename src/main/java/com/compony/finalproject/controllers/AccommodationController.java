package com.compony.finalproject.controllers;

import com.compony.finalproject.dto.AccommodationDto;
import com.compony.finalproject.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/accommodations")
public class AccommodationController {

    private final AccommodationService accommodationService;

    @Autowired
    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping
    public String showAccommodations(Model model) {
        List<AccommodationDto> accommodationDtos = accommodationService.gelAll();
        model.addAttribute("accommodationDtos", accommodationDtos);
        return "accommodations";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("accommodationDto", new AccommodationDto());
        return "createAccommodation";
    }

    @PostMapping("/create")
    public String createAccommodation(@ModelAttribute AccommodationDto accommodationDto) {
        accommodationService.createAccommodation(accommodationDto);
        return "redirect:/accommodations";
    }

    @GetMapping("/edit/{id}")
    public String showEditAccommodation(@PathVariable Long id, Model model) {
        AccommodationDto accommodationDto = accommodationService.getByID(id);
        model.addAttribute("accommodationDto", accommodationDto);
        return "editAccommodation";
    }

    @PostMapping("/edit/{id}")
    public String editAccommodation(@PathVariable Long id, @ModelAttribute AccommodationDto accommodationDto) {
        accommodationDto.setId(id);
        accommodationService.editAccommodation(accommodationDto);
        return "redirect:/accommodations";
    }

    @GetMapping("/filter")
    public String filterAccommodations(
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam(name = "availableFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate availableFrom,
            @RequestParam(name = "availableTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate availableTo,
            @RequestParam(name = "price", required = false) String price,
            @RequestParam(name = "rating", required = false) Integer rating,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {
        Page<AccommodationDto> filteredAccommodations = accommodationService.searchAccommodation(city, country,
                availableFrom, availableTo, price, rating, PageRequest.of(page, size));
        model.addAttribute("accommodations", filteredAccommodations);
        return "accommodations";
    }
}
