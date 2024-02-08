package com.compony.finalproject.repository;

import com.compony.finalproject.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    List<Accommodation> findByCityContainingOrCountryContaining(String city, String country);
}
