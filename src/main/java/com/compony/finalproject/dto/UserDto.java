package com.compony.finalproject.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String city;
    private String country;
    private String username;
    private String password;
    private String dateOfBirth;
    private String email;
}
