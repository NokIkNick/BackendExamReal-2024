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
        boolean onMemory = false;
        ApplicationConfig app = ApplicationConfig.getInstance()
                .initiateServer()
                .setExceptionHandling()
                .startServer(7070).setRoutes(Routes.getCarRoutes(isTesting, onMemory))
                .configureCors()
                .checkSecurityRoles(isTesting);

    }
}