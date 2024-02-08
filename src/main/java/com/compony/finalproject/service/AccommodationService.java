package com.compony.finalproject.service;

import com.compony.finalproject.dto.AccommodationDto;
import com.compony.finalproject.mappers.AccommodationMapper;
import com.compony.finalproject.model.Accommodation;
import com.compony.finalproject.repository.AccommodationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<AccommodationDto> filterAccommodations(String city, String country) {
        return accommodationRepository
                .findByCityContainingOrCountryContaining(city, country)
                .stream()
                .map(accommodationMapper::toDto)
                .collect(Collectors.toList());
    }


}
