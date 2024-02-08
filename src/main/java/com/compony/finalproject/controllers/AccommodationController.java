package com.compony.finalproject.controllers;

import com.compony.finalproject.dto.AccommodationDto;
import com.compony.finalproject.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            Model model) {
        List<AccommodationDto> filteredAccommodations = accommodationService.filterAccommodations(city, country);
        model.addAttribute("accommodations", filteredAccommodations);
        return "accommodations";
    }
}
