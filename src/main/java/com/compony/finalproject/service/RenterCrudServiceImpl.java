package com.compony.finalproject.service;

import com.compony.finalproject.dto.RenterDto;
import com.compony.finalproject.mappers.RenterMapper;
import com.compony.finalproject.repository.RenterRepository;
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
public class RenterCrudServiceImpl implements CrudService<RenterDto>{

    private final RenterRepository renterRepository;

    @Autowired
    public RenterCrudServiceImpl(RenterRepository renterRepository) {
        this.renterRepository = renterRepository;
    }

    /**
     * Получение объекта арендатора по его идентификатору.
     *
     * @param id идентификатор арендатора
     * @return объект DTO арендатора
     * @throws NoSuchElementException если арендатор не найден
     */
    @Override
    public RenterDto getById(Integer id) {
        log.info("Getting renter by id {}", id);

        return renterRepository.findById(id)
                .map(RenterMapper::toDTO)
                .orElseThrow(() -> {
                    log.warn("Renter not found with id: {}", id);
                    return new NoSuchElementException("Renter not found with id: " + id);
                });
    }

    /**
     * Извлекает всех арендаторов из репозитория и возвращает коллекцию их DTO.
     *
     * @return коллекция DTO арендаторов
     * @throws NoSuchElementException, если после отображения найден пустой элемент
     */
    @Override
    public Collection<RenterDto> getAll() {
        log.info("Getting all renters");
        return renterRepository.findAll()
                .stream()
                .map(RenterMapper::toDTO)
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
     * Сохраняет арендатора и записывает информацию о сохранении в журнал.
     *
     * @param item DTO арендатора
     * @throws IllegalArgumentException, если переданный объект является пустым
     */
    @Override
    public void create(RenterDto item) {
        log.info("Saving renter: {}", item);

        Optional.ofNullable(item)
                .map(RenterMapper::toEntity)
                .map(renter -> {
                    renterRepository.save(renter);
                    log.info("Renter saved successfully: {}", renter);
                    return renter; // Возвращаем сущность для цепочки, если это необходимо дальше
                })
                .orElseThrow(() -> new IllegalArgumentException("Cannot save null DTO or Entity"));
    }

    /**
     * Обновляет данные арендатора и записывает информацию о сохранении в журнал.
     *
     * @param item DTO арендатора
     * @throws IllegalArgumentException, если переданный объект является пустым
     */
    @Override
    public void update(RenterDto item) {
        log.info("Updating renter: {}", item);

        Optional.ofNullable(item)
                .map(RenterMapper::toEntity)
                .map(renter -> {
                    renterRepository.save(renter);
                    log.info("Renter updated successfully: {}", renter);
                    return renter; // Возвращаем сущность для цепочки, если это необходимо дальше
                })
                .orElseThrow(() -> new IllegalArgumentException("Cannot update null DTO or Entity"));
    }

    /**
     * Удаляет арендатора по его идентификатору.
     *
     * @param id идентификатор арендатора, который необходимо удалить
     * @throws IllegalArgumentException если переданный идентификатор равен null
     */
    @Override
    public void delete(Integer id) {
        Optional.ofNullable(id)
                .ifPresentOrElse(
                        existingId -> {
                            log.info("Deleting a renter by ID: {}", existingId);
                            renterRepository.deleteById(existingId);
                        },
                        () -> {
                            log.warn("The renter cannot be deleted. The ID cannot be null.");
                            throw new IllegalArgumentException("The renter cannot be deleted. The ID cannot be null.");
                        }
                );
    }
}
