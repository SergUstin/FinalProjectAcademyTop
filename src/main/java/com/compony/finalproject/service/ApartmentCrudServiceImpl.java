package com.compony.finalproject.service;

import com.compony.finalproject.dto.ApartmentDto;
import com.compony.finalproject.mappers.ApartmentMapper;
import com.compony.finalproject.repository.ApartmentRepository;
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
public class ApartmentCrudServiceImpl implements CrudService<ApartmentDto> {

    private final ApartmentRepository apartmentRepository;

    @Autowired
    public ApartmentCrudServiceImpl(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    /**
     * Получение объекта апартаментов по его идентификатору.
     *
     * @param id идентификатор апартаментов
     * @return объект DTO апартаментов
     * @throws NoSuchElementException если апартамент не найден
     */
    @Override
    public ApartmentDto getById(Integer id) {
        log.info("Getting apartment by id {}", id);

        return apartmentRepository.findById(id)
                .map(ApartmentMapper.INSTANCE::toDto)
                .orElseThrow(() -> {
                    log.warn("Apartment not found with id: {}", id);
                    return new NoSuchElementException("Apartment not found with id: " + id);
                });
    }

    /**
     * Извлекает все апартаменты из репозитория и возвращает коллекцию их DTO.
     *
     * @return коллекция DTO апартаментов
     * @throws NoSuchElementException, если после отображения найден пустой элемент
     */
    @Override
    public Collection<ApartmentDto> getAll() {
        log.info("Getting all apartments");
        return apartmentRepository.findAll()
                .stream()
                .map(ApartmentMapper.INSTANCE::toDto)
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
     * Сохраняет апартаменты и записывает информацию о сохранении в журнал.
     *
     * @param item DTO апартаментов
     * @throws IllegalArgumentException, если переданный объект является пустым
     */
    @Override
    public void create(ApartmentDto item) {
        log.info("Saving apartment: {}", item);

        Optional.ofNullable(item)
                .map(ApartmentMapper.INSTANCE::toEntity)
                .map(apartment -> {
                    apartmentRepository.save(apartment);
                    log.info("Apartment saved successfully: {}", apartment);
                    return apartment; // Возвращаем сущность для цепочки, если это необходимо дальше
                })
                .orElseThrow(() -> new IllegalArgumentException("Cannot save null DTO or Entity"));

    }

    @Override
    public void update(ApartmentDto item) {
        log.info("Updating apartment: {}", item);

        Optional.ofNullable(item)
                .map(ApartmentMapper.INSTANCE::toEntity)
                .map(apartment -> {
                    apartmentRepository.save(apartment);
                    log.info("Apartment updated successfully: {}", apartment);
                    return apartment; // Возвращаем сущность для цепочки, если это необходимо дальше
                })
                .orElseThrow(() -> new IllegalArgumentException("Cannot update null DTO or Entity"));
    }

    /**
     * Удаляет апартаменты по их идентификатору.
     *
     * @param id идентификатор апартамент, которые необходимо удалить
     * @throws IllegalArgumentException если переданный идентификатор равен null
     */
    @Override
    public void delete(Integer id) {
        Optional.ofNullable(id)
                .ifPresentOrElse(
                        existingId -> {
                            log.info("Deleting a apartment by ID: {}", existingId);
                            apartmentRepository.deleteById(existingId);
                        },
                        () -> {
                            log.warn("The apartment cannot be deleted. The ID cannot be null.");
                            throw new IllegalArgumentException("The apartment cannot be deleted. The ID cannot be null.");
                        }
                );
    }
}
