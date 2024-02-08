package com.compony.finalproject.service.impl;

import com.compony.finalproject.model.User;
import com.compony.finalproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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



}