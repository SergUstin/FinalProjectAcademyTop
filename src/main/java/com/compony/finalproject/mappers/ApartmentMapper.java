package com.compony.finalproject.mappers;

import com.compony.finalproject.dto.ApartmentDto;
import com.compony.finalproject.model.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApartmentMapper {
    ApartmentMapper INSTANCE = Mappers.getMapper(ApartmentMapper.class);
    ApartmentDto toDto(Apartment apartment);
    Apartment toEntity(ApartmentDto apartmentDto);
}
