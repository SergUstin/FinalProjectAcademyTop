package com.company.finalproject.service.impl;

import com.company.finalproject.model.User;
import com.company.finalproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    public void testGetById() {
        // Arrange
        Long testId = 1L;
        User user = new User(); // Создаем тестового пользователя
        when(repository.findById(testId)).thenReturn(Optional.of(user)); // Мокируем поведение userRepository

        // Act
        User result = service.getById(testId);

        // Assert
        assertEquals(user, result); // Проверяем, что получили ожидаемого пользователя
    }

    @Test
    public void testGetAll() {
        // Arrange
        List<User> userList = new ArrayList<>(); // Создаем тестовый список пользователей
        when(repository.findAll()).thenReturn(userList); // Мокируем поведение userRepository

        // Act
        List<User> result = service.getAll();

        // Assert
        assertEquals(userList, result); // Проверяем, что получили ожидаемый список пользователей
    }

    @Test
    public void testCreate() {
        // Arrange
        User user = new User(); // Создаем тестового пользователя

        // Act
        service.create(user);

        // Assert
        verify(repository).save(user); // Проверяем, что метод save был вызван с переданным пользователем
    }

    @Test
    public void testEdit() {
        // Создание фиктивного объекта User
        User user = new User();

        // Задание ожидаемого поведения при вызове метода findById
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        // Вызов метода edit
        service.edit(1L, user);

        // Проверка, что метод findById был вызван один раз
        verify(repository, times(1)).findById(1L);

        // Проверка, что метод save был вызван один раз с аргументом user
        verify(repository, times(1)).save(user);
    }

    @Test
    public void testGetUserIdByUsername() {
        // Создание фиктивного объекта User
        User user = new User();
        user.setId(123L);
        when(repository.findByUsername("testUser")).thenReturn(user);

        Long userId = service.getUserIdByUsername("testUser");
        assertEquals(123L, userId);
    }

    @Test
    public void testSetRatingByFullName() {
        // Создание фиктивного объекта User
        User user = new User();
        user.setFullName("John Doe");
        user.setNumberOfRatings(5L);
        user.setRating(4);
        when(repository.findByFullName("John Doe")).thenReturn(user);

        service.setRatingByFullName("John Doe", 5);

        // Проверка, что методы setRating и updateStatus были вызваны
        assertEquals(6L, user.getNumberOfRatings());
        assertEquals(4, user.getRating());
        verify(repository, times(1)).save(user);
    }



}