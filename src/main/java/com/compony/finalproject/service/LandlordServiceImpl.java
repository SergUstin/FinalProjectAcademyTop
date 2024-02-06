package com.compony.finalproject.service;

import com.compony.finalproject.dto.LandlordDto;
import com.compony.finalproject.mappers.LandlordMapper;
import com.compony.finalproject.model.Landlord;
import com.compony.finalproject.repository.LandlordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LandlordServiceImpl implements CrudService<LandlordDto> {

    private final LandlordRepository landlordRepository;
    private final LandlordMapper landlordMapper;

    @Autowired
    public LandlordServiceImpl(LandlordRepository landlordRepository, LandlordMapper landlordMapper) {
        this.landlordRepository = landlordRepository;
        this.landlordMapper = landlordMapper;
    }

    @Override
    public LandlordDto getById(Integer id) {
        return landlordMapper.toDto(landlordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Landlord not found with id: " + id)));
    }

    @Override
    public List<LandlordDto> getAll() {
        return landlordRepository.findAll().stream()
                .map(landlordMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LandlordDto create(LandlordDto item) {
        Landlord landlord = landlordMapper.toEntity(item);
        return landlordMapper.toDto(landlordRepository.save(landlord));
    }

    @Override
    public LandlordDto update(Integer id, LandlordDto landlordDto) {
        if (!landlordRepository.existsById(id)) {
            throw new EntityNotFoundException("Landlord not found with id: " + id);
        }
        Landlord updatedLandlord = landlordMapper.toEntity(landlordDto);
        updatedLandlord.setId(id);
        return landlordMapper.toDto(landlordRepository.save(updatedLandlord));
    }

    @Override
    public void delete(Integer id) {
        if (!landlordRepository.existsById(id)) {
            throw new EntityNotFoundException("Landlord not found with id: " + id);
        }
        landlordRepository.deleteById(id);
    }
}
