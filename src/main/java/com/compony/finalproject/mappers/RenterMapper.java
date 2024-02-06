package com.compony.finalproject.mappers;

import com.compony.finalproject.dto.RenterDto;
import com.compony.finalproject.model.Renter;

import java.util.List;
import java.util.stream.Collectors;


public class RenterMapper {

    public static RenterDto toDTO(Renter renter) {
        RenterDto dto = new RenterDto();
        dto.setId(renter.getId());
        dto.setFullName(renter.getFullName());
        dto.setPhoneNumber(renter.getPhoneNumber());
        dto.setEmail(renter.getEmail());
        dto.setRating(renter.getRating());
        return dto;
    }

    public static Renter toEntity(RenterDto dto) {
        Renter renter = new Renter();
        renter.setId(dto.getId());
        renter.setFullName(dto.getFullName());
        renter.setPhoneNumber(dto.getPhoneNumber());
        renter.setEmail(dto.getEmail());
        renter.setRating(dto.getRating());
        return renter;
    }

    public static List<RenterDto> toDTOList(List<Renter> renters) {
        return renters.stream().map(RenterMapper::toDTO).collect(Collectors.toList());
    }

    public static List<Renter> fromDTOList(List<RenterDto> dtos) {
        return dtos.stream().map(RenterMapper::toEntity).collect(Collectors.toList());
    }
}
