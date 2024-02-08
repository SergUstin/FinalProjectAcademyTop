package com.compony.finalproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String city;
    private String country;
    private String username;
    private String password;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    private Integer rating;
    private String email;
}
