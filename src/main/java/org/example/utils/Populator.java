package org.example.utils;

import jakarta.persistence.EntityManagerFactory;
import org.example.config.HibernateConfig;
import org.example.daos.CarDAO;
import org.example.model.Car;
import org.example.model.Seller;

import java.time.LocalDate;

public class Populator {

    public static void populate(boolean isTesting) {
        CarDAO cd = CarDAO.getInstance(isTesting);
        EntityManagerFactory emf;
        if(isTesting) {
            emf = HibernateConfig.getEntityManagerFactoryConfig(isTesting);
        }else {
            emf = HibernateConfig.getEntityManagerFactoryConfig(isTesting);
        }

        //String brand, String model, String make, int year, LocalDate firstRegistrationDate, double price
        Car car1 = new Car("Volkswagen", "Polo", "Volkswagen", 2007, LocalDate.of(2010,9,12), 125000);
        Car car2 = new Car("Volkswagen", "Up", "Volkswagen", 2005, LocalDate.of(2007,3,8), 100000);
        Car car3 = new Car("Citroen", "Cactus 3", "Citroen", 2003, LocalDate.of(2005,1,28), 190000);
        Car car4 = new Car("Lamborghini", "Fast", "Lamborghini", 2011, LocalDate.of(2020,5,2), 2900000);
        Car car5 = new Car("BMW", "Cool", "BMW", 2011, LocalDate.of(2020,5,2), 2900000);



        //String email, String firstName, String lastName, long phone, String city
        Seller seller1 = new Seller("Morten@Seller.dk", "Morten", "Petersen", 42517854, "København");
        Seller seller2 = new Seller("Lara@Seller.dk", "Lara", "Croft", 84933223, "The Jungle");

        seller1.addCar(car1);
        seller1.addCar(car2);
        seller2.addCar(car3);
        seller2.addCar(car4);



        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(seller1);
            em.persist(seller2);
            em.persist(car5);
            em.getTransaction().commit();
        }

    }
}
