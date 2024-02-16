package com.company.finalproject.service.impl;

import com.company.finalproject.repository.AccommodationRepository;
import com.company.finalproject.model.Accommodation;
import com.company.finalproject.repository.UserRepository;
import com.company.finalproject.service.CRUDService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class AccommodationServiceImpl implements CRUDService<Accommodation> {
    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;
    @Autowired
    public AccommodationServiceImpl(AccommodationRepository accommodationRepository, UserRepository userRepository) {
        this.accommodationRepository = accommodationRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Accommodation getById(Long id) {
        log.debug("Getting Accommodation by id: {}", id);
        return accommodationRepository.findById(id).orElseThrow(NoSuchElementException::new);    }
    @Override
    public List<Accommodation> getAll() {
        log.debug("Getting all Accommodations");
        return accommodationRepository.findAll();
    }
    @Override
    public void create(Accommodation item) {
        log.debug("Saving new Accommodation: {}", item);
        accommodationRepository.save(item);
    }
    @Override
    public void edit(Long id, Accommodation item) {
        log.debug("Editing Accommodation with id: {}", id);
        if (accommodationRepository.existsById(id)) {
            accommodationRepository.save(item);
        } else {
            log.error("Accommodation with id {} does not exist", id);
            throw new NoSuchElementException("Accommodation not found");
        }
    }
    public void createAndAddLandlordId(Accommodation accommodation, Long landlordId) {
        log.debug("Editing Accommodation with userId: {}", landlordId);
        if (userRepository.existsById(landlordId)) {
            accommodation.setLandlordId(landlordId);
            accommodation.setNumberOfRatings(0L);
            accommodation.setRating(0);
            create(accommodation);
        } else {
            log.error("Accommodation with id {} does not exist", landlordId);
            throw new NoSuchElementException("Accommodation not found");
        }
    }
    public List<Accommodation> filterAccommodation(String city, String country, String price) {
        if (city == null && country == null && price == null) {
            throw new IllegalArgumentException("At least one search parameter must be provided");
        }
        log.info("Filtering accommodations by city: {}, country: {}, price: {}", city, country, price);
        return accommodationRepository.findByCityContainingOrCountryContainingOrPrice(city, country, price);
    }
    @Transactional
    public void book(LocalDate userAvailableFrom, LocalDate userAvailableTo, Long accommodationId, Long userId) {
        Accommodation accommodation = getById(accommodationId);
        // Проверить, попадают ли вводимые даты в уже существующий интервал дат у сущности
        if (accommodation.getAvailableFrom() != null && accommodation.getAvailableTo() != null) {
            if (isOverlap(userAvailableFrom, userAvailableTo, accommodation) || isAdjacent(userAvailableFrom, userAvailableTo, accommodation)) {
                log.error("Failed to book accommodation with id {}: Input dates overlap with existing dates", accommodationId);
                throw new IllegalArgumentException("Введенные даты попадают в уже существующий интервал дат");
            }
        }
        // Если даты не попадают в уже существующий интервал дат, то ввести эти данные в базу данных
        accommodation.setAvailableFrom(userAvailableFrom);
        accommodation.setAvailableTo(userAvailableTo);
        accommodation.setUserId(userId);
        accommodationRepository.save(accommodation);
        log.info("Accommodation with id {} booked successfully for user {}", accommodationId, userId);
    }
    private boolean isOverlap(LocalDate userAvailableFrom, LocalDate userAvailableTo, Accommodation accommodation) {
        return userAvailableFrom.isBefore(accommodation.getAvailableTo()) && userAvailableTo.isAfter(accommodation.getAvailableFrom());
    }
    private boolean isAdjacent(LocalDate userAvailableFrom, LocalDate userAvailableTo, Accommodation accommodation) {
        return userAvailableFrom.isEqual(accommodation.getAvailableTo()) || userAvailableTo.isEqual(accommodation.getAvailableFrom());
    }
    public int addRatingAndUpdateAverage(Long accommodationId, int newRating) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> {
                    log.error("Failed to add rating: Accommodation with id {} not found", accommodationId);
                    return new IllegalArgumentException("Accommodation with id " + accommodationId + " not found");
                });
        // Проверить, находится ли новая оценка в диапазоне от 1 до 5.
        if (newRating < 1 || newRating > 5) {
            throw new IllegalArgumentException("Оценка должна быть в диапазоне от 1 до 5");
        }
        // Увеличить количество оценок на 1.
        accommodation.setNumberOfRatings(accommodation.getNumberOfRatings() + 1);
        long countRatings = accommodation.getNumberOfRatings();
        // Проверить, существует ли уже рейтинг жилья.
        Integer currentRating = accommodation.getRating();
        if (currentRating == null || currentRating == 0) {
            // Если рейтинг жилья еще не установлен или равен 0, то просто присвоить ему значение новой оценки.
            accommodation.setRating(newRating);
        } else {
            // Если рейтинг жилья уже существует, то рассчитать новую среднюю оценку.
            long newAverageRating = ((currentRating * (countRatings - 1)) + newRating) / countRatings;
            // Округляем среднюю оценку до двух знаков после запятой
            newAverageRating = (long) (Math.round(newAverageRating * 100.0) / 100.0);
            accommodation.setRating((int) newAverageRating);
        }
        accommodationRepository.save(accommodation);
        log.info("Rating {} added to accommodation with id {}", newRating, accommodationId);
        return accommodation.getRating();
    }
}
