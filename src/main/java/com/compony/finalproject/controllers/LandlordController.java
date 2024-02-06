package com.compony.finalproject.controllers;

import com.compony.finalproject.dto.LandlordDto;
import com.compony.finalproject.service.impl.LandlordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/landlords")
public class LandlordController {

    private final LandlordServiceImpl landlordService;

    @Autowired
    public LandlordController(LandlordServiceImpl landlordService) {
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
    public ResponseEntity<List<LandlordDto>> getAll() {
        try {
            List<LandlordDto> landlordDto = landlordService.getAll();
            return ResponseEntity.ok(landlordDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/register-landlord")
    public String create(@RequestBody LandlordDto landlordDto) {
        // В этом методе данные, введенные веб-формой, автоматически связываются с объектом Landlord
        // Пароль следует зашифровать перед сохранением в базу данных
        // Пример шифрования пароля с помощью BCrypt:
        String encryptedPassword = BCrypt.hashpw(landlordDto.getPassword(), BCrypt.gensalt());
        landlordDto.setPassword(encryptedPassword);
        try {
            landlordService.create(landlordDto);
            return "redirect:/apartments";
        } catch (Exception e) {
            return "redirect:/notFound";
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LandlordDto> update(@PathVariable Integer id, @RequestBody LandlordDto landlordDto) {
        try {
            landlordService.update(id, landlordDto);
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
