package com.compony.finalproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "accommodations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String country;
    @Column(name = "available_from")
    private LocalDate availableFrom;
    @Column(name = "available_to")
    private LocalDate availableTo;
    private String price;
    private Integer rating;


    @Column(name = "user_id")
    private Long userId;

    @Column(name = "landlord_id")
    private Long landlordId;
}
