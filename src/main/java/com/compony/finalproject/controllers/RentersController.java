package com.compony.finalproject.controllers;

import com.compony.finalproject.dto.RenterDto;
import com.compony.finalproject.service.RenterCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/renters")
public class RentersController {

    private final RenterCrudServiceImpl renterCrudService;

    @Autowired
    public RentersController(RenterCrudServiceImpl renterCrudService) {
        this.renterCrudService = renterCrudService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RenterDto> getById(@PathVariable Integer id) {
        try {
            RenterDto renterDto = renterCrudService.getById(id);
            return ResponseEntity.ok(renterDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<Collection<RenterDto>> getAll() {
        try {
            Collection<RenterDto> renterDto = renterCrudService.getAll();
            return ResponseEntity.ok(renterDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<RenterDto> create(@RequestBody RenterDto renterDto) {
        try {
            renterCrudService.create(renterDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(renterDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<RenterDto> update(@RequestBody RenterDto renterDto) {
        try {
            renterCrudService.update(renterDto);
            return ResponseEntity.status(HttpStatus.OK).body(renterDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RenterDto> deleteById(@PathVariable Integer id) {
        try {
            renterCrudService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
