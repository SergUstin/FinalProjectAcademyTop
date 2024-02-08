package com.compony.finalproject.service;

import com.compony.finalproject.dto.AccommodationDto;
import com.compony.finalproject.mappers.AccommodationMapper;
import com.compony.finalproject.model.Accommodation;
import com.compony.finalproject.repository.AccommodationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationMapper accommodationMapper;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository,
                                AccommodationMapper accommodationMapper) {
        this.accommodationRepository = accommodationRepository;
        this.accommodationMapper = accommodationMapper;
    }

    public List<AccommodationDto> gelAll() {
        return accommodationRepository.findAll()
                .stream()
                .map(accommodationMapper::toDto)
                .collect(Collectors.toList());
    }

    public void createAccommodation(AccommodationDto accommodationDto) {
        Accommodation accommodation = accommodationMapper.toEntity(accommodationDto);
        accommodationRepository.save(accommodation);
    }

    public void editAccommodation(AccommodationDto accommodationDto) {
        Accommodation accommodation = accommodationMapper.toEntity(accommodationDto);
        accommodationRepository.save(accommodation);
    }

    public AccommodationDto getByID(Long id) {
        return accommodationMapper.toDto(accommodationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Landlord not found with id: " + id)));
    }

    public Page<AccommodationDto> searchAccommodation(String city,
                                                      String country,
                                                      LocalDate availableFrom,
                                                      LocalDate availableTo,
                                                      String price,
                                                      Integer rating, Pageable pageable) {
        return accommodationRepository.findByCityAndCountryAndAvailableFromAndAvailableToAndPriceAndRating(city,
                        country, availableFrom, availableTo, price, rating, pageable)
                .map(accommodationMapper::toDto);
    }

    public Page<AccommodationDto> searchAccommodation(String city,
                                                      LocalDate availableFrom,
                                                      LocalDate availableTo,
                                                      String price,
                                                      Integer rating, Pageable pageable) {
        return accommodationRepository.findByCityAndAvailableFromAndAvailableToAndPriceAndRating(city,
                        availableFrom, availableTo, price, rating, pageable)
                .map(accommodationMapper::toDto);
    }

    public Page<AccommodationDto> searchAccommodation(String city, String country, Integer rating, Pageable pageable) {
        return accommodationRepository.findByCityAndCountryAndRating(city, country, rating, pageable)
                .map(accommodationMapper::toDto);
    }

    public Page<AccommodationDto> searchAccommodation(String city, Pageable pageable) {
        return accommodationRepository.findByCity(city, pageable)
                .map(accommodationMapper::toDto);
    }


}
