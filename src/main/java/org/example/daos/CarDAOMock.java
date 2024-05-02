package org.example.daos;

import org.example.dtos.CarDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarDAOMock extends MemoryDAO<CarDTO, Integer>{

    private static CarDAOMock instance;
    public static CarDAOMock getInstance(){
        if(instance == null){
            instance = new CarDAOMock();
        }
        return instance;
    }


    public List<CarDTO> getByYear(int year){
        return super.memory.stream().filter(x -> x.getYear() == year).toList();
    }

    public Map<String, Double> getTotalPriceOfEachCategory(){
        return super.memory.stream().collect(Collectors.groupingBy(CarDTO::getBrand, Collectors.summingDouble(CarDTO::getPrice)));
    }

}
