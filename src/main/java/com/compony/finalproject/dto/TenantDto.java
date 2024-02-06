package com.compony.finalproject.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TenantDto {
    Integer id;
    String userName;
    String password;
    String fullName;
    String phoneNumber;
    String email;
    Long rating;
}
