package com.compony.finalproject.service;

import com.compony.finalproject.dto.LandlordDto;
import com.compony.finalproject.mappers.LandlordMapper;
import com.compony.finalproject.repository.LandlordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LandlordCrudServiceImpl implements CrudService<LandlordDto> {

    private final LandlordRepository landlordRepository;

    @Override
    public LandlordDto getById(Integer id) {
        log.info("Get by id {}", id);
        return LandlordMapper.INSTANCE.toDto(landlordRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Landlord not found with id: " + id)));
    }

    @Override
    public Collection<LandlordDto> getAll() {
        log.info("Get All");
        return landlordRepository.findAll()
                .stream()
                .map(LandlordMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void create(LandlordDto item) {
        log.info("Create");
        landlordRepository.save(LandlordMapper.INSTANCE.toEntity(item));
    }

    @Override
    public void update(LandlordDto item) {
        log.info("Update");
        landlordRepository.save(LandlordMapper.INSTANCE.toEntity(item));
    }

    @Override
    public void delete(Integer id) {
        log.info("Delete by ID: {}", id);
        landlordRepository.deleteById(id);
    }
}
