package com.company.finalproject.repository;

import com.company.finalproject.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с моделью Accommodation.
 */
@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>,
        JpaSpecificationExecutor<Accommodation> {

    /**
     * Поиск размещений по указанному городу, стране или цене.
     *
     * @param city    Город для поиска.
     * @param country Страна для поиска.
     * @param price   Цена для поиска.
     * @return Список размещений, удовлетворяющих критериям поиска.
     */
    List<Accommodation> findByCityContainingOrCountryContainingOrPrice(String city, String country, String price);
}
