package com.compony.finalproject.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LandlordDto {
    Integer id;
    String fullName;
    String phoneNumber;
    String email;
    Long rating;
    List<RenterDto> rentersList;
    List<ApartmentDto> apartmentsList;
}
