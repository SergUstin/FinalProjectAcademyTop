package com.compony.finalproject.controllers;

import com.compony.finalproject.model.Accommodation;
import com.compony.finalproject.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/accommodations")
public class AccommodationController {

    private final AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationController(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    @GetMapping
    public String showAccommodations(Model model) {
        List<Accommodation> accommodation = accommodationRepository.findAll();
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
        accommodationRepository.save(accommodation);
        return "redirect:/accommodations";
    }

    @GetMapping("/edit/{id}")
    public String showEditAccommodation(@PathVariable Long id, Model model) {
        Accommodation accommodation = accommodationRepository.findById(id).orElseThrow();
        model.addAttribute("accommodation", accommodation);
        return "editAccommodation";
    }

    @PostMapping("/edit/{id}")
    public String editAccommodation(@PathVariable Long id, @ModelAttribute Accommodation accommodation) {
        accommodation.setId(id);
        accommodationRepository.save(accommodation);
        return "redirect:/accommodations";
    }

    @GetMapping("/filter")
    public String filterAccommodations(
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "country", required = false) String country,
            Model model) {
        List<Accommodation> filteredAccommodations = accommodationRepository.findByCityOrCountry(city, country);
        model.addAttribute("accommodations", filteredAccommodations);
        return "filteredAccommodations";
    }

//    @RequestMapping(value = "/accommodations", method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<AccommodationDto>> getAllAccommodations() {
//        List<AccommodationDto> list = accommodationServiceImpl.getAll();
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/accommodations", method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_VALUE,
//            consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<AccommodationDto>> getFilteredAccommodations(@RequestBody Filter filter) {
//        List<AccommodationDto> list = accommodationServiceImpl.getFilteredAccommodations(filter);
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
}
