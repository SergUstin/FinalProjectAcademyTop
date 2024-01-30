package com.compony.finalproject.mappers;

import com.compony.finalproject.dto.RenterDto;
import com.compony.finalproject.model.Renter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RenterMapper {
    RenterMapper INSTANCE = Mappers.getMapper(RenterMapper.class);
    RenterDto toDto(Renter renter);
    Renter toEntity(RenterDto renterDto);
}
