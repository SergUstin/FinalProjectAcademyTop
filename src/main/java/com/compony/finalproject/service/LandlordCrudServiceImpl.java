package com.compony.finalproject.service;

import com.compony.finalproject.dto.LandlordDto;
import com.compony.finalproject.mappers.LandlordMapper;
import com.compony.finalproject.repository.LandlordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class LandlordCrudServiceImpl implements CrudService<LandlordDto> {

    private final LandlordRepository landlordRepository;

    @Autowired
    public LandlordCrudServiceImpl(LandlordRepository landlordRepository) {
        this.landlordRepository = landlordRepository;
    }

    /**
     * Получение объекта арендодателя по его идентификатору.
     *
     * @param id идентификатор арендодателя
     * @return объект DTO арендодателя
     * @throws NoSuchElementException если арендодатель не найден
     */
    @Override
    public LandlordDto getById(Integer id) {
        log.info("Getting landlord by id {}", id);

        return landlordRepository.findById(id)
                .map(LandlordMapper.INSTANCE::toDto)
                .orElseThrow(() -> {
                    log.warn("Landlord not found with id: {}", id);
                    return new NoSuchElementException("Landlord not found with id: " + id);
                });
    }

    /**
     * Извлекает всех арендодателей из репозитория и возвращает коллекцию их DTO.
     *
     * @return коллекция DTO арендодателей
     * @throws NoSuchElementException, если после отображения найден пустой элемент
     */
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

    /**
     * Сохраняет арендодателя и записывает информацию о сохранении в журнал.
     *
     * @param item DTO арендодателя
     * @throws IllegalArgumentException, если переданный объект является пустым
     */
    @Override
    public void create(LandlordDto item) {
        log.info("Saving landlord: {}", item);

        Optional.ofNullable(item)
                .map(LandlordMapper.INSTANCE::toEntity)
                .map(landlordEntity -> {
                    landlordRepository.save(landlordEntity);
                    log.info("Landlord saved successfully: {}", landlordEntity);
                    return landlordEntity; // Возвращаем сущность для цепочки, если это необходимо дальше
                })
                .orElseThrow(() -> new IllegalArgumentException("Cannot save null DTO or Entity"));
    }

    /**
     * Обновляет данные арендодателя и записывает информацию о сохранении в журнал.
     *
     * @param item DTO арендодателя
     * @throws IllegalArgumentException, если переданный объект является пустым
     */
    @Override
    public void update(LandlordDto item) {
        log.info("Updating landlord: {}", item);

        Optional.ofNullable(item)
                .map(LandlordMapper.INSTANCE::toEntity)
                .map(landlordEntity -> {
                    landlordRepository.save(landlordEntity);
                    log.info("Landlord updated successfully: {}", landlordEntity);
                    return landlordEntity; // Возвращаем сущность для цепочки, если это необходимо дальше
                })
                .orElseThrow(() -> new IllegalArgumentException("Cannot update null DTO or Entity"));
    }

    /**
     * Удаляет арендодателя по его идентификатору.
     *
     * @param id идентификатор арендодателя, который необходимо удалить
     * @throws IllegalArgumentException если переданный идентификатор равен null
     */
    @Override
    public void delete(Integer id) {
        Optional.ofNullable(id)
                .ifPresentOrElse(
                        existingId -> {
                            log.info("Deleting a landlord by ID: {}", existingId);
                            landlordRepository.deleteById(existingId);
                        },
                        () -> {
                            log.warn("The landlord cannot be deleted. The ID cannot be null.");
                            throw new IllegalArgumentException("The landlord cannot be deleted. The ID cannot be null.");
                        }
                );
    }
}
