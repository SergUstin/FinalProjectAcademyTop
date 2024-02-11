package com.company.finalproject.service.impl;

import com.company.finalproject.model.Accommodation;
import com.company.finalproject.repository.AccommodationRepository;
import com.company.finalproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceImplTest {

    @Mock
    private AccommodationRepository accommodationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccommodationServiceImpl service;

    @Test
    public void testGetById() {
        // Arrange
        Long id = 1L;
        Accommodation mockAccommodation = new Accommodation();
        when(accommodationRepository.findById(id)).thenReturn(Optional.of(mockAccommodation));

        // Act
        Accommodation result = service.getById(id);

        // Assert
        assertEquals(mockAccommodation, result);
    }

    @Test
    public void testGetAll() {
        // Arrange
        List<Accommodation> mockList = Arrays.asList(new Accommodation(), new Accommodation());
        when(accommodationRepository.findAll()).thenReturn(mockList);

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
        verify(accommodationRepository).save(newAccommodation);
    }

    @Test
    public void testEditExistingAccommodation() {
        // Создаем объект Accommodation
        Accommodation accommodation = new Accommodation();

        // Устанавливаем, что метод existsById вернет false
        when(accommodationRepository.existsById(1L)).thenReturn(true);

        // Вызываем метод edit
        service.edit(1L, accommodation);

        // Проверяем, что метод save был вызван у accommodationRepository
        verify(accommodationRepository).save(accommodation);
    }

    @Test
    public void testEditNonExistingAccommodation() {
        // Создаем объект Accommodation
        Accommodation accommodation = new Accommodation();

        // Устанавливаем, что метод existsById вернет false
        when(accommodationRepository.existsById(1L)).thenReturn(false);

        // Вызываем метод edit
        assertThrows(NoSuchElementException.class, () -> {
            service.edit(1L, accommodation);
        });
    }

    @Test
    public void testFilterAccommodation() {
        // Arrange
        String city = "New York";
        String country = "USA";
        String price = "100";
        List<Accommodation> mockList = Arrays.asList(new Accommodation(), new Accommodation());
        when(accommodationRepository.findByCityContainingOrCountryContainingOrPrice(city, country, price)).thenReturn(mockList);

        // Act
        List<Accommodation> result = service.filterAccommodation(city, country, price);

        // Assert
        assertEquals(mockList, result);
    }

    @Test
    public void testCreateNonExistingAccommodation() {
        Long userId = 2L;
        Accommodation nonExistingAccommodation = new Accommodation();
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> {
            service.createAndAddLandlordId(nonExistingAccommodation, userId);
        });
    }

    @Test
    void testBook_whenNoOverlapOrAdjacentDates_thenAccommodationIsBookedSuccessfully() {
        // Arrange
        LocalDate userAvailableFrom = LocalDate.of(2022, 8, 1);
        LocalDate userAvailableTo = LocalDate.of(2022, 8, 10);
        Long accommodationId = 1L;
        Long userId = 123L;
        Accommodation accommodation = new Accommodation();
        accommodation.setAvailableFrom(LocalDate.of(2022, 8, 15));
        accommodation.setAvailableTo(LocalDate.of(2022, 8, 20));

        when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation));

        // Act
        service.book(userAvailableFrom, userAvailableTo, accommodationId, userId);

        // Assert
        assertEquals(userAvailableFrom, accommodation.getAvailableFrom());
        assertEquals(userAvailableTo, accommodation.getAvailableTo());
        assertEquals(userId, accommodation.getUserId());
        verify(accommodationRepository, times(1)).save(any());
    }

    @Test
    void testBook_whenOverlapOrAdjacentDates_thenExceptionIsThrown() {
        // Arrange
        LocalDate userAvailableFrom = LocalDate.of(2022, 8, 1);
        LocalDate userAvailableTo = LocalDate.of(2022, 8, 10);
        Long accommodationId = 1L;
        Long userId = 123L;
        Accommodation accommodation = new Accommodation();
        accommodation.setAvailableFrom(LocalDate.of(2022, 8, 5));
        accommodation.setAvailableTo(LocalDate.of(2022, 8, 15));

        when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> service.book(userAvailableFrom, userAvailableTo, accommodationId, userId));
    }

    @Test
    public void testAddRatingAndUpdateAverage() {
        Long accommodationId = 1L;
        int newRating = 4;

        Accommodation accommodation = new Accommodation();
        accommodation.setNumberOfRatings(5L);
        accommodation.setRating(3);

        when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation));

        int result = service.addRatingAndUpdateAverage(accommodationId, newRating);

        assertEquals(3, result);
    }

    @Test
    public void testAddRatingAndUpdateAverageWithInvalidRating() {
        Long accommodationId = 1L;
        int newRating = 6;

        Accommodation accommodation = new Accommodation();
        when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation));

        assertThrows(IllegalArgumentException.class, () -> {
            service.addRatingAndUpdateAverage(accommodationId, newRating);
        });
    }


}