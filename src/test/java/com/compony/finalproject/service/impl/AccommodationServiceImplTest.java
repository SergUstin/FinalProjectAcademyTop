package com.compony.finalproject.service.impl;

import com.compony.finalproject.model.Accommodation;
import com.compony.finalproject.repository.AccommodationRepository;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceImplTest {

    @Mock
    private AccommodationRepository repository;

    @InjectMocks
    private AccommodationServiceImpl service;

    @Test
    public void testGetById() {
        // Arrange
        Long id = 1L;
        Accommodation mockAccommodation = new Accommodation();
        when(repository.findById(id)).thenReturn(Optional.of(mockAccommodation));

        // Act
        Accommodation result = service.getById(id);

        // Assert
        assertEquals(mockAccommodation, result);
    }

    @Test
    public void testGetAll() {
        // Arrange
        List<Accommodation> mockList = Arrays.asList(new Accommodation(), new Accommodation());
        when(repository.findAll()).thenReturn(mockList);

        // Act
        List<Accommodation> result = service.getAll();

        // Assert
        assertEquals(mockList, result);
    }

    @Test
    public void testCreate() {
        // Arrange
        Accommodation newAccommodation = new Accommodation();

        // Act
        service.create(newAccommodation);

        // Assert
        verify(repository).save(newAccommodation);
    }

    @Test
    public void testFilterAccommodation() {
        // Arrange
        String city = "New York";
        String country = "USA";
        String price = "100";
        List<Accommodation> mockList = Arrays.asList(new Accommodation(), new Accommodation());
        when(repository.findByCityContainingOrCountryContainingOrPrice(city, country, price)).thenReturn(mockList);

        // Act
        List<Accommodation> result = service.filterAccommodation(city, country, price);

        // Assert
        assertEquals(mockList, result);
    }


}