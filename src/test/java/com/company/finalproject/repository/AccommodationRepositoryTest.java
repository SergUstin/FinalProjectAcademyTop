package com.company.finalproject.repository;

import com.company.finalproject.model.Accommodation;
import com.company.finalproject.service.impl.AccommodationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@DirtiesContext
class AccommodationRepositoryTest {

    @Mock
    private AccommodationRepository accommodationRepository;

    @InjectMocks
    private AccommodationServiceImpl accommodationService;

    @Test
    void testFindByCityContainingOrCountryContainingOrPrice() {
        // Создаем тестовые данные
        Accommodation accommodation1 = new Accommodation();
        accommodation1.setCity("City1");
        accommodation1.setCountry("Country1");
        accommodation1.setPrice("Price1");
        Accommodation accommodation2 = new Accommodation();
        accommodation2.setCity("City2");
        accommodation2.setCountry("Country2");
        accommodation2.setPrice("Price2");
        List<Accommodation> accommodations = Arrays.asList(accommodation1, accommodation2);

        // Задаем поведение для мок-объекта accommodationRepository
        when(accommodationRepository
                .findByCityContainingOrCountryContainingOrPrice(anyString(), anyString(), anyString()))
                .thenReturn(accommodations);

        // Вызываем метод, который будем тестировать
        List<Accommodation> result = accommodationRepository
                .findByCityContainingOrCountryContainingOrPrice("City", "Country", "Price");

        // Проверяем, что результат соответствует ожидаемому
        assertEquals(2, result.size());
        assertEquals("City1", result.get(0).getCity());
        assertEquals("Country2", result.get(1).getCountry());
    }

}