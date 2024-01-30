package com.compony.finalproject.mappers;

import com.compony.finalproject.dto.LandlordDto;
import com.compony.finalproject.model.Landlord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LandlordMapper {
    LandlordMapper INSTANCE = Mappers.getMapper(LandlordMapper.class);
    LandlordDto toDto(Landlord landlord);
    Landlord toEntity(LandlordDto landlordDto);
}
