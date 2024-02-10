package com.compony.finalproject.service.impl;

import com.compony.finalproject.model.Accommodation;
import com.compony.finalproject.repository.AccommodationRepository;
import com.compony.finalproject.service.CRUDService;
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

    public void create(Accommodation accommodation, Long userId) {
        accommodation.setLandlordId(userId);
        accommodationRepository.save(accommodation);
    }

    public List<Accommodation> filterAccommodation(String city, String country, String price) {
        return accommodationRepository.findByCityContainingOrCountryContainingOrPrice(city, country, price);
    }

    @Transactional
    public void book(LocalDate userAvailableFrom, LocalDate userAvailableTo, Long accommodationId, Long userId) {
        Accommodation accommodation = getById(accommodationId);

        // Проверить, попадают ли вводимые даты в уже существующий интервал дат у сущности
        if (accommodation.getAvailableFrom() != null && accommodation.getAvailableTo() != null) {
            if (isOverlap(userAvailableFrom, userAvailableTo, accommodation) || isAdjacent(userAvailableFrom, userAvailableTo, accommodation)) {
                throw new IllegalArgumentException("Введенные даты попадают в уже существующий интервал дат");
            }
        }

        // Если даты не попадают в уже существующий интервал дат, то ввести эти данные в базу данных
        accommodation.setAvailableFrom(userAvailableFrom);
        accommodation.setAvailableTo(userAvailableTo);
        accommodation.setUserId(userId);
        accommodationRepository.save(accommodation);
    }

    private boolean isOverlap(LocalDate userAvailableFrom, LocalDate userAvailableTo, Accommodation accommodation) {
        return userAvailableFrom.isBefore(accommodation.getAvailableTo()) && userAvailableTo.isAfter(accommodation.getAvailableFrom());
    }

    private boolean isAdjacent(LocalDate userAvailableFrom, LocalDate userAvailableTo, Accommodation accommodation) {
        return userAvailableFrom.isEqual(accommodation.getAvailableTo()) || userAvailableTo.isEqual(accommodation.getAvailableFrom());
    }

    public double addRatingAndUpdateAverage(Long accommodationId, double newRating) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new IllegalArgumentException("Жилье с указанным идентификатором не найдено"));

        // Проверить, находится ли новая оценка в диапазоне от 1 до 5.
        if (newRating < 1 || newRating > 5) {
            throw new IllegalArgumentException("Оценка должна быть в диапазоне от 1 до 5");
        }

        // Увеличить количество оценок на 1.
        accommodation.incrementNumberOfRatings();

        long countRatings = accommodation.getNumberOfRatings();

        // Проверить, существует ли уже рейтинг жилья.
        Double currentRating = accommodation.getRating();
        if (currentRating == null || currentRating == 0.0) {
            // Если рейтинг жилья еще не установлен или равен 0, то просто присвоить ему значение новой оценки.
            accommodation.setRating(newRating);
        } else {
            // Если рейтинг жилья уже существует, то рассчитать новую среднюю оценку.
            double newAverageRating = ((currentRating * (countRatings - 1)) + newRating) / countRatings;

            // Округляем среднюю оценку до двух знаков после запятой
            newAverageRating = Math.round(newAverageRating * 100.0) / 100.0;

            accommodation.setRating(newAverageRating);
        }

        accommodationRepository.save(accommodation);

        return accommodation.getRating();
    }






}
