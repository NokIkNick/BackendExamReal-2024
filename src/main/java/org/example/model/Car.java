package org.example.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String brand;
    private String model;
    private String make;
    private int year;
    @Temporal(TemporalType.DATE)
    private LocalDate firstRegistrationDate;
    private double price;



    public Car(String brand, String model, String make, int year, LocalDate firstRegistrationDate, double price) {
        this.brand = brand;
        this.model = model;
        this.make = make;
        this.year = year;
        this.firstRegistrationDate = firstRegistrationDate;
        this.price = price;
    }

}
