package com.compony.finalproject.service.impl;

import com.compony.finalproject.model.Accommodation;
import com.compony.finalproject.repository.AccommodationRepository;
import com.compony.finalproject.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccommodationServiceImpl implements CRUDService<Accommodation> {

    private final AccommodationRepository repository;

    @Autowired
    public AccommodationServiceImpl(AccommodationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Accommodation getById(Long id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Accommodation> getAll() {
        return repository.findAll();
    }

    @Override
    public void create(Accommodation item) {
        repository.save(item);
    }

    public List<Accommodation> filterAccommodation(String city, String country, String price) {
        return repository.findByCityContainingOrCountryContainingOrPrice(city, country, price);
    }
}
