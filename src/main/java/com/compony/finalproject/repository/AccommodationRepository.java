package com.compony.finalproject.repository;

import com.compony.finalproject.model.Accommodation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    Page<Accommodation> findByCityAndCountryAndAvailableFromAndAvailableToAndPriceAndRating(String city, String country, LocalDate availableFrom, LocalDate availableTo, String price, Integer rating, Pageable pageable);

    Page<Accommodation> findByCityAndAvailableFromAndAvailableToAndPriceAndRating(String city, LocalDate availableFrom, LocalDate availableTo, String price, Integer rating, Pageable pageable);

    Page<Accommodation> findByCityAndCountryAndRating(String city, String country, Integer rating, Pageable pageable);

    Page<Accommodation> findByCity(String city, Pageable pageable);
}
