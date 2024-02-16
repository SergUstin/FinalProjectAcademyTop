package com.company.finalproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FinalProjectApplicationTest {

    @Test
    public void contextLoads() {
        FinalProjectApplication finalProjectApplication = new FinalProjectApplication(); // Создаем экземпляр класса
        // Проверяем, что экземпляр класса создается без ошибок
        assertNotNull(finalProjectApplication);
    }

}