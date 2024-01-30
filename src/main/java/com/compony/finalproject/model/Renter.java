package com.compony.finalproject.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "renter")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Renter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "phone_number")
    String phoneNumber;

    String email;

    @Column(name = "data_registration")
    LocalDateTime dataRegistration;

    @OneToMany(mappedBy = "renter", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "landlords_id")
    List<Landlord> landlords;

    @OneToMany(mappedBy = "renter", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "apartment_id")
    List<Apartment> apartments;

}
