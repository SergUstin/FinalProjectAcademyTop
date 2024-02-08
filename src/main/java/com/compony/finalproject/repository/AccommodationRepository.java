package com.compony.finalproject.repository;

import com.compony.finalproject.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long>,
        JpaSpecificationExecutor<Accommodation> {
    List<Accommodation> findByCityOrCountry(String city, String country);
}
