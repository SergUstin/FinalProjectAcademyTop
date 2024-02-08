package com.compony.finalproject.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccommodationDto {
    Long id;
    String city;
    String country;
    LocalDate availableFrom;
    LocalDate availableTo;
    String price;
    Integer rating;
}
