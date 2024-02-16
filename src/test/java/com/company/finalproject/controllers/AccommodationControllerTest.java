package com.company.finalproject.controllers;

import com.company.finalproject.model.Accommodation;
import com.company.finalproject.service.impl.AccommodationServiceImpl;
import com.company.finalproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AccommodationController.class)
class AccommodationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccommodationServiceImpl accommodationService;

    @MockBean
    private UserServiceImpl userService;

    @Test
    void testShowAccommodations() throws Exception {
        List<Accommodation> accommodations = new ArrayList<>();
        // Здесь добавьте логику для заполнения списка accommodations

        when(accommodationService.getAll()).thenReturn(accommodations);

        mockMvc.perform(get("/accommodations"))
                .andExpect(status().isOk())
                .andExpect(view().name("accommodations"))
                .andExpect(model().attribute("accommodation", accommodations));
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/accommodations/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createAccommodation"))
                .andExpect(model().attributeExists("accommodation"));
    }

    @Test
    void testCreateAccommodation() throws Exception {
        String username = "testUser";
        when(userService.getUserIdByUsername(username)).thenReturn(1L);

        mockMvc.perform(post("/accommodations/create")
                        .param("username", username))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accommodations"));

        verify(accommodationService, times(1)).createAndAddLandlordId(any(), anyLong());
    }

    @Test
    void testShowEditAccommodation() throws Exception {
        Long accommodationId = 1L;
        Accommodation accommodation = new Accommodation();
        // Здесь добавьте логику для заполнения accommodation

        when(accommodationService.getById(accommodationId)).thenReturn(accommodation);

        mockMvc.perform(get("/accommodations/edit/{id}", accommodationId))
                .andExpect(status().isOk())
                .andExpect(view().name("editAccommodation"))
                .andExpect(model().attribute("accommodation", accommodation));
    }

    @Test
    void testEditAccommodation() throws Exception {
        Long accommodationId = 1L;
        Accommodation accommodation = new Accommodation();
        // Здесь добавьте логику для заполнения accommodation

        mockMvc.perform(post("/accommodations/edit/{id}", accommodationId)
                        .flashAttr("accommodation", accommodation))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accommodations"));

        verify(accommodationService, times(1)).edit(accommodationId, accommodation);
    }

    @Test
    void testFilterAccommodations() throws Exception {
        String city = "SomeCity";
        String country = "SomeCountry";
        String price = "100"; // Example price

        List<Accommodation> filteredAccommodations = new ArrayList<>();
        // Здесь добавьте логику для заполнения filteredAccommodations с учетом переданных параметров

        when(accommodationService.filterAccommodation(city, country, price)).thenReturn(filteredAccommodations);

        mockMvc.perform(get("/accommodations/filter")
                        .param("city", city)
                        .param("country", country)
                        .param("price", price))
                .andExpect(status().isOk())
                .andExpect(view().name("filteredAccommodations"))
                .andExpect(model().attribute("accommodations", filteredAccommodations));
    }

    @Test
    void testShowBookingForm() throws Exception {
        Long accommodationId = 1L;
        Accommodation accommodation = new Accommodation();
        // Здесь добавьте логику для заполнения accommodation

        when(accommodationService.getById(accommodationId)).thenReturn(accommodation);

        mockMvc.perform(get("/accommodations/booking/{id}", accommodationId))
                .andExpect(status().isOk())
                .andExpect(view().name("toBook"))
                .andExpect(model().attribute("accommodation", accommodation));
    }

    @Test
    void testToBookAccommodation() throws Exception {
        Long accommodationId = 1L;
        LocalDate availableFrom = LocalDate.now().plusDays(1);
        LocalDate availableTo = LocalDate.now().plusDays(5);
        String username = "someUser";

        when(userService.getUserIdByUsername(username)).thenReturn(1L);

        mockMvc.perform(post("/accommodations/booking/{id}", accommodationId)
                        .param("availableFrom", availableFrom.toString())
                        .param("availableTo", availableTo.toString())
                        .param("username", username))
                .andExpect(status().is3xxRedirection()) // Assuming a successful booking redirects to the accommodations page
                .andExpect(redirectedUrl("/accommodations"));
    }

    @Test
    void testShowRatingForm() throws Exception {
        Long accommodationId = 1L;
        Accommodation accommodation = new Accommodation();
        // Здесь добавьте логику для заполнения accommodation

        when(accommodationService.getById(accommodationId)).thenReturn(accommodation);

        mockMvc.perform(get("/accommodations/setRating/{id}", accommodationId))
                .andExpect(status().isOk())
                .andExpect(view().name("giveRatingAccommodation"))
                .andExpect(model().attribute("accommodation", accommodation));
    }

    @Test
    void testSetRating() throws Exception {
        Long accommodationId = 1L;
        Integer rating = 5; // Example rating
        Integer newAverageRating = 4; // Example new average rating

        when(accommodationService.addRatingAndUpdateAverage(accommodationId, rating)).thenReturn(newAverageRating);

        mockMvc.perform(post("/accommodations/setRating/{id}", accommodationId)
                        .param("rating", String.valueOf(rating)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accommodations"));
    }

}