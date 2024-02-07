package com.compony.finalproject.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Setter
@Getter
@Entity
@Table(name = "apartments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String city;
    private String address;

    @OneToOne
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

    @OneToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
}

