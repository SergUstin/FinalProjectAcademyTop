package com.compony.finalproject.mappers;

import com.compony.finalproject.dto.ApartmentDto;
import com.compony.finalproject.model.Apartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApartmentMapper {


    private final LandlordMapper landlordMapper;
    private final TenantMapper tenantMapper;

    @Autowired
    public ApartmentMapper(LandlordMapper landlordMapper, TenantMapper tenantMapper) {
        this.landlordMapper = landlordMapper;
        this.tenantMapper = tenantMapper;
    }

    public ApartmentDto toDto(Apartment apartment) {
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setId(apartment.getId());
        apartmentDto.setCity(apartment.getCity());
        apartmentDto.setAddress(apartment.getAddress());
        apartmentDto.setLandlord(landlordMapper.toDto(apartment.getLandlord()));
        apartmentDto.setTenant(tenantMapper.toDto(apartment.getTenant()));
        return apartmentDto;
    }

    public Apartment toEntity(ApartmentDto apartmentDto) {
        Apartment apartment = new Apartment();
        apartment.setId(apartmentDto.getId());
        apartment.setCity(apartmentDto.getCity());
        apartment.setAddress(apartmentDto.getAddress());
        apartment.setLandlord(landlordMapper.toEntity(apartmentDto.getLandlord()));
        apartment.setTenant(tenantMapper.toEntity(apartmentDto.getTenant()));
        return apartment;
    }
}
