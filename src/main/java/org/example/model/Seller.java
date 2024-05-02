package org.example.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

    @Id
    private String email;

    private String firstName;
    private String lastName;
    private long phone;
    private String city;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<Car> cars;

    public Seller(String email, String firstName, String lastName, long phone, String city) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.city = city;
        this.cars = new ArrayList<>();
    }

    public void addCar(Car car) {
        if(cars != null && !cars.contains(car)) {
            cars.add(car);
        }
    }

}
