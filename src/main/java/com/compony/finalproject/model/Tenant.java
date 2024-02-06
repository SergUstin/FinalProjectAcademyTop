package com.compony.finalproject.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "tenants")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "full_name")
    String fullName;
    @Column(name = "phone_number")
    String phoneNumber;

    String email;

    Long rating;

    @Column(name = "data_registration")
    @CreationTimestamp
    LocalDateTime dataRegistration;
}