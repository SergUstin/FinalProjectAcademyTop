package com.compony.finalproject.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RenterDto {
    Integer id;
    String fullName;
    String phoneNumber;
    String email;
    Long rating;
    List<LandlordDto> landlordsList;
    List<ApartmentDto> apartmentsList;

}
