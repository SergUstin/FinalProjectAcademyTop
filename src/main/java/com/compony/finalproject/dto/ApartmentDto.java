package com.compony.finalproject.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApartmentDto {
    Integer id;
    String city;
    String address;
    LandlordDto landlord;
//    TenantDto tenant;
}
