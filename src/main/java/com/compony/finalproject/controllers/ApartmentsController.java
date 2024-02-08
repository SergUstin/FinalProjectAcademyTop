package com.compony.finalproject.controllers;

import com.compony.finalproject.dto.ApartmentDto;
import com.compony.finalproject.dto.LandlordDto;
import com.compony.finalproject.mappers.ApartmentMapper;
import com.compony.finalproject.mappers.LandlordMapper;
import com.compony.finalproject.model.Apartment;
import com.compony.finalproject.model.Landlord;
import com.compony.finalproject.service.impl.ApartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apartment")
public class ApartmentsController {

    private final ApartmentServiceImpl apartmentService;
    private final ApartmentMapper apartmentMapper;
    private final LandlordMapper landlordMapper;

    @Autowired
    public ApartmentsController(ApartmentServiceImpl apartmentService, ApartmentMapper apartmentMapper, LandlordMapper landlordMapper) {
        this.apartmentService = apartmentService;
        this.apartmentMapper = apartmentMapper;
        this.landlordMapper = landlordMapper;
    }

    @PostMapping("/add")
    public String addApartment(@ModelAttribute("apartmentDto") ApartmentDto apartmentDto,
                               @ModelAttribute("landlordDto")LandlordDto landlordDto) {
        // Конвертируем данные из DTO в объект Apartment
        Apartment apartment = apartmentMapper.toEntity(apartmentDto);
        // Используем только необходимые поля из объекта LandlordDto
        String fullName = landlordDto.getFullName();
        String phoneNumber = landlordDto.getPhoneNumber();
        String email = landlordDto.getEmail();
        Long rating = landlordDto.getRating();

        // Конвертируем данные из LandlordDto в объект Landlord
        Landlord landlord = landlordMapper.toEntity(new LandlordDto(fullName, phoneNumber, email, rating));

        // Привязываем владельца к квартире и сохраняем оба объекта
        apartment.setLandlord(landlord);
        apartmentService.create(apartmentMapper.toDto(apartment));

        return "redirect:/apartments";

    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ApartmentDto> getById(@PathVariable Integer id) {
//        try {
//            ApartmentDto apartmentDto = apartmentService.getById(id);
//            return ResponseEntity.ok(apartmentDto);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ApartmentDto>> getAll() {
//        try {
//            List<ApartmentDto> apartmentDto = apartmentService.getAll();
//            return ResponseEntity.ok(apartmentDto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<ApartmentDto> create(@RequestBody ApartmentDto apartmentDto) {
//        try {
//            apartmentService.create(apartmentDto);
//            return ResponseEntity.status(HttpStatus.CREATED).body(apartmentDto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ApartmentDto> update(@PathVariable Integer id, @RequestBody ApartmentDto apartmentDto) {
//        try {
//            apartmentService.update(id, apartmentDto);
//            return ResponseEntity.status(HttpStatus.OK).body(apartmentDto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ApartmentDto> deleteById(@PathVariable Integer id) {
//        try {
//            apartmentService.delete(id);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
}
