package com.compony.finalproject.mappers;

import com.compony.finalproject.dto.LandlordDto;
import com.compony.finalproject.model.Landlord;
import org.springframework.stereotype.Component;

@Component
public class LandlordMapper {

    public LandlordDto toDto(Landlord landlord) {
        LandlordDto landlordDto = new LandlordDto();
        landlordDto.setId(landlord.getId());
        landlordDto.setUserName(landlord.getUserName());
        landlordDto.setPassword(landlord.getPassword());
        landlordDto.setFullName(landlord.getFullName());
        landlordDto.setPhoneNumber(landlord.getPhoneNumber());
        landlordDto.setEmail(landlord.getEmail());
        landlordDto.setRating(landlord.getRating());
        return landlordDto;
    }

    public Landlord toEntity(LandlordDto landlordDto) {
        Landlord landlord = new Landlord();
        landlord.setId(landlordDto.getId());
        landlord.setUserName(landlordDto.getUserName());
        landlord.setPassword(landlordDto.getPassword());
        landlord.setFullName(landlordDto.getFullName());
        landlord.setPhoneNumber(landlordDto.getPhoneNumber());
        landlord.setEmail(landlordDto.getEmail());
        landlord.setRating(landlordDto.getRating());
        return landlord;
    }
}
