package com.compony.finalproject.service;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

/**
 * Интерфейс LandlordService предоставляет методы для работы с арендаторами (landlords).
 * @param <T> тип объекта DTO, используемого для передачи данных
 */
public interface CrudService<T> {

    /**
     * Получает информацию об объекте по его идентификатору.
     * @param id идентификатор объекта
     * @return объект DTO, содержащий информацию об объекте
     * @throws EntityNotFoundException если объект с указанным идентификатором не найден
     */
    T getById(Integer id);

    /**
     * Получает список всех объектов.
     * @return список объектов DTO, содержащих информацию обо всех объектах
     */
    List<T> getAll();

    /**
     * Добавляет нового объекта на основе переданного объекта DTO.
     * @param item объект DTO с информацией о новом объекте
     * @return объект DTO с информацией о добавленном объекта
     */
    T create(T item);

    /**
     * Обновляет информацию о существующем объекте на основе его идентификатора и переданного объекта DTO.
     * @param id идентификатор объекта
     * @param item объект DTO с обновленной информацией об объекте
     * @return объект DTO с обновленной информацией об объекте
     * @throws EntityNotFoundException если объект с указанным идентификатором не найден
     */
    T update(Integer id, T item);

    /**
     * Удаляет объект по его идентификатору.
     * @param id идентификатор объекта
     * @throws EntityNotFoundException если объект с указанным идентификатором не найден
     */
    void delete(Integer id);
}
