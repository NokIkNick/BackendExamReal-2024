package org.example.daos;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.example.exceptions.ApiException;
import org.example.model.Car;
import org.example.model.Seller;

import java.util.HashSet;
import java.util.Set;

public class CarDAO extends DAO<Car, Integer>{

    private static CarDAO instance;

    public static CarDAO getInstance(boolean isTesting){
        if(instance == null){
            instance = new CarDAO(Car.class, isTesting);
        }
        return instance;
    }

    private CarDAO(Class<Car> carClass, boolean isTesting) {
        super(carClass, isTesting);
    }

    public void addCarToSeller(int sellerId, int carId) throws ApiException {
        if(sellerId < 0 && carId < 0){
            throw new ApiException(400, "Key value lower than possible id");
        }
        Car foundCar;
        Seller foundSeller;
        try(var em = emf.createEntityManager()){
            foundCar = em.find(Car.class, carId);
            foundSeller = em.find(Seller.class, sellerId);
        }
        if(foundCar == null){
            throw new ApiException(404, "Car with key:"+ carId + " not found");
        }
        if(foundSeller == null){
            throw new ApiException(404, "Seller with key:"+ sellerId + " not found");
        }
        foundSeller.addCar(foundCar);

        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.merge(foundSeller);
            em.getTransaction().commit();
        }
    }

    public Set<Car> getCarsBySeller(int sellerId) throws ApiException {
        if(sellerId < 0){
            throw new ApiException(400, "Key value lower than possible id");
        }
        Seller foundSeller;
        try(var em = emf.createEntityManager()){
            foundSeller = em.find(Seller.class, sellerId);
        }
        if(foundSeller == null){
            throw new ApiException(404, "Seller with key:"+ sellerId + " not found");
        }
        return new HashSet<>(foundSeller.getCars());
    }

}
