package com.compony.finalproject.service.impl;

import com.compony.finalproject.dto.ApartmentDto;
import com.compony.finalproject.dto.LandlordDto;
import com.compony.finalproject.mappers.ApartmentMapper;
import com.compony.finalproject.model.Apartment;
import com.compony.finalproject.model.Landlord;
import com.compony.finalproject.model.Tenant;
import com.compony.finalproject.repository.ApartmentRepository;
import com.compony.finalproject.repository.LandlordRepository;
import com.compony.finalproject.repository.TenantRepository;
import com.compony.finalproject.service.CrudService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApartmentServiceImpl {

    private final ApartmentRepository apartmentRepository;
    private final ApartmentMapper apartmentMapper;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, ApartmentMapper apartmentMapper) {
        this.apartmentRepository = apartmentRepository;
        this.apartmentMapper = apartmentMapper;
    }

    public List<ApartmentDto> getAllApartmentsWithLandlordDetails() {
        List<Apartment> apartments = apartmentRepository.findAll();
        List<ApartmentDto> apartmentDtos = new ArrayList<>();

        for (Apartment apartment : apartments) {
            Landlord landlord = apartment.getLandlord();
            ApartmentDto apartmentDto = new ApartmentDto();
            apartmentDto.setId(apartment.getId());
            apartmentDto.setCity(apartment.getCity());
            apartmentDto.setAddress(apartment.getAddress());

            // Заполнение информации о владельце
            if (landlord != null) {
                LandlordDto landlordDto = new LandlordDto();
                landlordDto.setFullName(landlord.getFullName());
                landlordDto.setPhoneNumber(landlord.getPhoneNumber());
                landlordDto.setEmail(landlord.getEmail());
                landlordDto.setRating(landlord.getRating());
                apartmentDto.setLandlord(landlordDto);
            }

            apartmentDtos.add(apartmentDto);
        }
        return apartmentDtos;
    }

    public void create(ApartmentDto dto) {
    }

//    @Override
//    public ApartmentDto getById(Integer id) {
//        return apartmentMapper.toDto(apartmentRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Landlord not found with id: " + id)));
//    }
//
//    @Override
//    public List<ApartmentDto> getAll() {
//        return apartmentRepository.findAll().stream()
//                .map(apartmentMapper::toDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public ApartmentDto create(ApartmentDto item) {
//        Apartment apartment = apartmentMapper.toEntity(item);
//        apartment = apartmentRepository.save(apartment);
//        return apartmentMapper.toDto(apartment);
//
//    }
//
//    @Override
//    public ApartmentDto update(Integer id, ApartmentDto item) {
//        Apartment apartment = apartmentMapper.toEntity(item);
//        apartment.setId(id);
//        apartment = apartmentRepository.save(apartment);
//        return apartmentMapper.toDto(apartment);
//    }
//
//    @Override
//    public void delete(Integer id) {
//        if (!apartmentRepository.existsById(id)) {
//            throw new EntityNotFoundException("Landlord not found with id: " + id);
//        }
//        apartmentRepository.deleteById(id);
//    }
}
