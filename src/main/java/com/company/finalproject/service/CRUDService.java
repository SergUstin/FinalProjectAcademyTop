package com.company.finalproject.service;

import java.util.List;

/**
 * Интерфейс CRUDService предоставляет базовые методы для выполнения операций CRUD (Create, Read, Update, Delete) с объектами определенного типа.
 *
 * @param <T> тип объекта
 */
public interface CRUDService<T> {

    /**
     * Метод получения объекта по его идентификатору.
     *
     * @param id идентификатор объекта, который необходимо получить
     * @return объект типа T
     */
    T getById(Long id);

    /**
     * Метод получения всех объектов определенного типа.
     *
     * @return список объектов типа T
     */
    List<T> getAll();

    /**
     * Метод создания нового объекта.
     *
     * @param item объект, который необходимо создать
     */
    void create(T item);

    /**
     * Редактирует элемент с указанным идентификатором.
     *
     * @param id идентификатор элемента, который нужно отредактировать
     * @param <T> тип элемента
     * @param item новое значение элемента
     */
    void edit(Long id, T item);
}
