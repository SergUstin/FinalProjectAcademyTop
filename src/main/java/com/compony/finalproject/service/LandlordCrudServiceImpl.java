package com.compony.finalproject.service;

import com.compony.finalproject.dto.LandlordDto;
import com.compony.finalproject.mappers.LandlordMapper;
import com.compony.finalproject.model.Landlord;
import com.compony.finalproject.repository.LandlordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class LandlordCrudServiceImpl implements CrudService<LandlordDto> {

    private final LandlordRepository landlordRepository;

    @Override
    public LandlordDto getById(Integer id) {
        log.info("Getting landlord by id {}", id);

        Optional<Landlord> optionalLandlord = landlordRepository.findById(id);

        if (optionalLandlord.isPresent()) {
            return LandlordMapper.INSTANCE.toDto(optionalLandlord.get());
        } else {
            log.warn("Landlord not found with id: {}", id);
            throw new NoSuchElementException("Landlord not found with id: " + id);
        }
    }

    @Override
    public Collection<LandlordDto> getAll() {
        log.info("Getting all landlords");
        return landlordRepository.findAll()
                .stream()
                .map(LandlordMapper.INSTANCE::toDto)
                .flatMap(dto -> {
                    if (dto == null) {
                        log.warn("Null element found after mapping");
                        throw new NoSuchElementException("Null element found after mapping");
                    } else {
                        return Stream.of(dto);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public void create(LandlordDto item) {
        log.info("Saving landlord: {}", item);

        if (item == null) {
            throw new IllegalArgumentException("Cannot save null DTO");
        }

        Landlord landlordEntity = LandlordMapper.INSTANCE.toEntity(item);

        if (landlordEntity == null) {
            throw new IllegalArgumentException("Cannot save null Entity");
        }

        landlordRepository.save(landlordEntity);
        log.info("Landlord saved successfully: {}", landlordEntity);
    }

    @Override
    public void update(LandlordDto item) {
        log.info("Updating landlord: {}", item);

        if (item == null) {
            throw new IllegalArgumentException("Cannot update null DTO");
        }

        Landlord landlordEntity = LandlordMapper.INSTANCE.toEntity(item);

        if (landlordEntity == null) {
            throw new IllegalArgumentException("Cannot update null Entity");
        }

        landlordRepository.save(landlordEntity);
        log.info("Landlord updated successfully: {}", landlordEntity);
    }

    @Override
    public void delete(Integer id) {
        if (id != null) {
            log.info("Delete by ID: {}", id);
            landlordRepository.deleteById(id);
        } else {
            log.warn("Unable to delete landlord. ID cannot be null.");
            throw new IllegalArgumentException("Unable to delete landlord. ID cannot be null.");
        }
    }
}
