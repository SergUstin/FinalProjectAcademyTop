package com.compony.finalproject.mappers;

import com.compony.finalproject.dto.AccommodationDto;
import com.compony.finalproject.model.Accommodation;
import org.springframework.stereotype.Component;

@Component
public class AccommodationMapper {
    public AccommodationDto toDto(Accommodation accommodation) {
        AccommodationDto accommodationDto = new AccommodationDto();
        accommodationDto.setId(accommodation.getId());
        accommodationDto.setCity(accommodation.getCity());
        accommodationDto.setCountry(accommodation.getCountry());
        accommodationDto.setAvailableFrom(accommodation.getAvailableFrom());
        accommodationDto.setAvailableTo(accommodation.getAvailableTo());
        accommodationDto.setPrice(accommodation.getPrice());
        accommodationDto.setRating(accommodation.getRating());
        return accommodationDto;
    }

    public Accommodation toEntity(AccommodationDto accommodationDto) {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(accommodationDto.getId());
        accommodation.setCity(accommodationDto.getCity());
        accommodation.setCountry(accommodationDto.getCountry());
        accommodation.setAvailableFrom(accommodationDto.getAvailableFrom());
        accommodation.setAvailableTo(accommodationDto.getAvailableTo());
        accommodation.setPrice(accommodationDto.getPrice());
        accommodation.setRating(accommodationDto.getRating());
        return accommodation;
    }
}
