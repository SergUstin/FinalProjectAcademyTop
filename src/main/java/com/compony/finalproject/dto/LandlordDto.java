package com.compony.finalproject.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;



@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LandlordDto {
    String fullName;
    String phoneNumber;
    String email;
    Long rating;

    public LandlordDto() {
    }

    public LandlordDto(String fullName, String phoneNumber, String email, Long rating) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.rating = rating;
    }
}
