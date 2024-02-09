package com.compony.finalproject.service.impl;

import com.compony.finalproject.model.Accommodation;
import com.compony.finalproject.model.User;
import com.compony.finalproject.repository.AccommodationRepository;
import com.compony.finalproject.service.CRUDService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccommodationServiceImpl implements CRUDService<Accommodation> {

    private final AccommodationRepository accommodationRepository;
    private final UserServiceImpl userService;

    @Autowired
    public AccommodationServiceImpl(AccommodationRepository accommodationRepository, UserServiceImpl userService) {
        this.accommodationRepository = accommodationRepository;
        this.userService = userService;
    }

    @Override
    public Accommodation getById(Long id) {
        return accommodationRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Accommodation> getAll() {
        return accommodationRepository.findAll();
    }

    @Override
    public void create(Accommodation item) {
        accommodationRepository.save(item);
    }

    public List<Accommodation> filterAccommodation(String city, String country, String price) {
        return accommodationRepository.findByCityContainingOrCountryContainingOrPrice(city, country, price);
    }

    @Transactional
    public void book(LocalDate userAvailableFrom, LocalDate userAvailableTo, Long id) {

        Accommodation accommodation = getById(id);

        // Проверить, попадают ли вводимые даты в уже существующий интервал дат у сущности
        if (accommodation.getAvailableFrom() != null && accommodation.getAvailableTo() != null) {
            if (isOverlap(userAvailableFrom, userAvailableTo, accommodation) || isAdjacent(userAvailableFrom, userAvailableTo, accommodation)) {
                throw new IllegalArgumentException("Введенные даты попадают в уже существующий интервал дат");
            }
        }




        // Если даты не попадают в уже существующий интервал дат, то ввести эти данные в базу данных
        accommodation.setAvailableFrom(userAvailableFrom);
        accommodation.setAvailableTo(userAvailableTo);
        accommodationRepository.save(accommodation);

    }

    private boolean isOverlap(LocalDate userAvailableFrom, LocalDate userAvailableTo, Accommodation accommodation) {
        return userAvailableFrom.isBefore(accommodation.getAvailableTo()) && userAvailableTo.isAfter(accommodation.getAvailableFrom());
    }

    private boolean isAdjacent(LocalDate userAvailableFrom, LocalDate userAvailableTo, Accommodation accommodation) {
        return userAvailableFrom.isEqual(accommodation.getAvailableTo()) || userAvailableTo.isEqual(accommodation.getAvailableFrom());
    }

}
