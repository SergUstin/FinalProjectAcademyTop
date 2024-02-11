package com.company.finalproject.repository;

import com.company.finalproject.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>,
        JpaSpecificationExecutor<Accommodation> {
    List<Accommodation> findByCityContainingOrCountryContainingOrPrice(String city, String country, String price);
}
