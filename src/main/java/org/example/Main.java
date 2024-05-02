package org.example;

import org.example.config.ApplicationConfig;
import org.example.config.Routes;
import org.example.daos.CarDAOMock;
import org.example.dtos.CarDTO;
import org.example.utils.Populator;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        boolean isTesting = false;
        boolean onMemory = true;
        ApplicationConfig app = ApplicationConfig.getInstance()
                .initiateServer()
                .setExceptionHandling()
                .startServer(7070).setRoutes(Routes.getCarRoutes(isTesting, onMemory))
                .checkSecurityRoles(isTesting);
        //Populator.populate(isTesting);

        CarDAOMock cdm = CarDAOMock.getInstance();
        try{
            cdm.create(new CarDTO(1,"testBrand","testModel1", "testMake1", 2009, LocalDate.now(),2500000));
            cdm.create(new CarDTO(1,"testBrand","testModel1", "testMake1", 2009, LocalDate.now(),2500000));
            cdm.create(new CarDTO(1,"testBrandTwo","testModel1", "testMake1", 2009, LocalDate.now(),2500000));
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(cdm.getTotalPriceOfEachCategory());

    }
}