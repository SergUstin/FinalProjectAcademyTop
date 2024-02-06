package com.compony.finalproject.mappers;

import com.compony.finalproject.dto.ApartmentDto;
import com.compony.finalproject.model.Apartment;

import java.util.List;
import java.util.stream.Collectors;


public class ApartmentMapper {

    public static ApartmentDto toDto(Apartment apartment) {
        ApartmentDto dto = new ApartmentDto();
        dto.setId(apartment.getId());
        dto.setCity(apartment.getCity());
        dto.setAddress(apartment.getAddress());
        dto.setLandlordId(apartment.getLandlord().getId());
        dto.setRenterId(apartment.getRenter().getId());
        return dto;
    }

    public static Apartment toEntity(ApartmentDto dto) {
        Apartment apartment = new Apartment();
        apartment.setId(dto.getId());
        apartment.setCity(dto.getCity());
        apartment.setAddress(dto.getAddress());
        return apartment;
    }

    public static List<ApartmentDto> toDTOList(List<Apartment> apartments) {
        return apartments.stream().map(ApartmentMapper::toDto).collect(Collectors.toList());
    }

    public static List<Apartment> fromDTOList(List<ApartmentDto> apartmentDtos) {
        return apartmentDtos.stream().map(ApartmentMapper::toEntity).collect(Collectors.toList());
    }
}
