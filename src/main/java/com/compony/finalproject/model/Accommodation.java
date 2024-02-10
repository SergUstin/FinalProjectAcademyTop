package com.compony.finalproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    private Double rating = 0.0;
    @Column(name = "number_of_rating")
    private Long numberOfRatings = 0L;


    @Column(name = "user_id")
    private Long userId;

    @Column(name = "landlord_id")
    private Long landlordId;

    @Transient
    public void incrementNumberOfRatings() {
        this.numberOfRatings++;
    }


//    @Transient
//    public static double calculateAverageRating(List<Double> ratings) {
//        if (ratings == null || ratings.isEmpty()) {
//            return 0.0;
//        }
//
//        double sum = 0.0;
//        for (double rating : ratings) {
//            sum += rating;
//        }
//
//        return sum / ratings.size();
//    }


}
