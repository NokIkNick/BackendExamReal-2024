package org.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Car;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarDTO implements DTO<Integer>{

    private int id;
    private String brand;
    private String model;
    private String make;
    private int year;
    private LocalDate firstRegistrationDate;
    private double price;

    public CarDTO(Car car) {
        this.id = car.getId();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.make = car.getMake();
        this.year = car.getYear();
        this.firstRegistrationDate = car.getFirstRegistrationDate();
        this.price = car.getPrice();
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }


}
