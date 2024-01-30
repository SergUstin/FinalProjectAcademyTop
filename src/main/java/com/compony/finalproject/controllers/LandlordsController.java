package com.compony.finalproject.controllers;

import com.compony.finalproject.dto.LandlordDto;
import com.compony.finalproject.service.LandlordCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/landlords")
public class LandlordsController {

    private final LandlordCrudServiceImpl landlordService;

    @Autowired
    public LandlordsController(LandlordCrudServiceImpl landlordService) {
        this.landlordService = landlordService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LandlordDto> getById(@PathVariable Integer id) {
        try {
            LandlordDto landlordDto = landlordService.getById(id);
            return ResponseEntity.ok(landlordDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<Collection<LandlordDto>> getAll() {
        try {
            Collection<LandlordDto> landlordDto = landlordService.getAll();
            return ResponseEntity.ok(landlordDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<LandlordDto> create(@RequestBody LandlordDto landlordDto) {
        try {
            landlordService.create(landlordDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(landlordDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<LandlordDto> update(@RequestBody LandlordDto landlordDto) {
        try {
            landlordService.update(landlordDto);
            return ResponseEntity.status(HttpStatus.OK).body(landlordDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LandlordDto> deleteById(@PathVariable Integer id) {
        try {
            landlordService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
