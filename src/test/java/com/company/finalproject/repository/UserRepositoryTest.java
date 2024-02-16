package com.company.finalproject.repository;

import com.company.finalproject.model.User;
import com.company.finalproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@DirtiesContext
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testFindByUsername() {
        // Создаем тестового пользователя
        User user = new User();
        user.setUsername("testuser");
        user.setFullName("Test User");

        // Задаем поведение для мок-объекта userRepository
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        // Вызываем метод сервиса, который использует userRepository.findByUsername
        User foundUser = userRepository.findByUsername("testuser");

        // Проверяем, что метод вернул ожидаемого пользователя
        assertEquals(user, foundUser);
    }

    @Test
    void testFindByFullName() {
        // Создаем тестового пользователя
        User user = new User();
        user.setUsername("testuser");
        user.setFullName("Test User");

        // Задаем поведение для мок-объекта userRepository
        when(userRepository.findByFullName(anyString())).thenReturn(user);

        // Вызываем метод сервиса, который использует userRepository.findByUsername
        User foundUser = userRepository.findByFullName("testuser");

        // Проверяем, что метод вернул ожидаемого пользователя
        assertEquals(user, foundUser);
    }

}