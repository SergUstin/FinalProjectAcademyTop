package com.compony.finalproject.mappers;

import com.compony.finalproject.dto.LandlordDto;
import com.compony.finalproject.model.Landlord;

import java.util.List;
import java.util.stream.Collectors;


public class LandlordMapper {

    public static LandlordDto toDto(Landlord landlord) {
        LandlordDto dto = new LandlordDto();
        dto.setId(landlord.getId());
        dto.setFullName(landlord.getFullName());
        dto.setPhoneNumber(landlord.getPhoneNumber());
        dto.setEmail(landlord.getEmail());
        dto.setRating(landlord.getRating());
        return dto;
    }

    public static Landlord toEntity(LandlordDto dto) {
        Landlord landlord = new Landlord();
        landlord.setId(dto.getId());
        landlord.setFullName(dto.getFullName());
        landlord.setPhoneNumber(dto.getPhoneNumber());
        landlord.setEmail(dto.getEmail());
        landlord.setRating(dto.getRating());
        return landlord;
    }

    public static List<LandlordDto> toDTOList(List<Landlord> landlords) {
        return landlords.stream().map(LandlordMapper::toDto).collect(Collectors.toList());
    }

    public static List<Landlord> fromDTOList(List<LandlordDto> dtos) {
        return dtos.stream().map(LandlordMapper::toEntity).collect(Collectors.toList());
    }
}
