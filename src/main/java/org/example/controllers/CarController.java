package org.example.controllers;

import io.javalin.http.Handler;
import io.javalin.http.UnprocessableContentResponse;
import org.example.daos.CarDAO;
import org.example.daos.CarDAOMock;
import org.example.daos.DAO;
import org.example.daos.IDAO;
import org.example.dtos.CarDTO;
import org.example.exceptions.ApiException;
import org.example.model.Car;
import org.example.utils.Updater;

import java.util.List;
import java.util.stream.Collectors;

public class CarController {

    private static CarController instance;
    private static IDAO carDao;
    private static boolean onMemory;

    public static CarController getInstance(boolean isTesting, boolean onMemory_) {
        if(instance == null) {
            instance = new CarController();
        }
        onMemory = onMemory_;
        if(!onMemory) {
            carDao = CarDAO.getInstance(isTesting);
        }else {
            carDao = CarDAOMock.getInstance();
        }
        return instance;
    }


    public Handler getAll(){
        return ctx -> {
            List cars = carDao.getAll();
            if(!onMemory){
                cars = cars.stream().map(x -> new CarDTO((Car) x)).toList();
            }
            ctx.json(cars);
        };
    }

    public Handler getById(){
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            if(id <= 0 || id > carDao.getAll().size()){
                throw new NumberFormatException();
            }
            Object car;
            if(onMemory){
                car = (CarDTO) carDao.getById(id);
            }else {
                car = (Car) carDao.getById(id);
            }

            if(car == null){
                throw new ApiException(404, "No item was found with key: "+id);
            }
            ctx.json(car);
        };
    }

    public Handler create(){
        return ctx -> {
            Object car;
            try{
                if(onMemory){
                    car = ctx.bodyAsClass(CarDTO.class);
                }else {
                    car = ctx.bodyAsClass(Car.class);
                }
            }catch (Exception e){
                throw new ApiException(400, "Bad request");
            }
            Object toReturn;
            if(onMemory){
                toReturn = (CarDTO) carDao.create(car);
            }else {
                toReturn = (Car) carDao.create(car);
                toReturn = new CarDTO((Car) toReturn);
            }

            if(toReturn == null){
                throw new ApiException(400, "No item was created");
            }
            ctx.json(toReturn);
        };
    }

    public Handler update(){
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            if(id <= 0 || id > carDao.getAll().size()){
                throw new NumberFormatException();
            }
            CarDTO input;
            try{
                input = ctx.bodyAsClass(CarDTO.class);
            }catch (Exception e){
                throw new ApiException(400, "Bad request");
            }

            if(input == null){
                throw new ApiException(400, "Invalid input "+id);
            }
            input.setId(id);

            Object updated;
            if(onMemory){
                updated = (CarDTO) carDao.update(input, id);
            }else {
                Car carEntity = new Car();
                carEntity = Updater.updateFromDTO(carEntity, input);
                carEntity.setId(id);
                updated = (Car) carDao.update(carEntity, id);
                updated = new CarDTO((Car) updated);
            }


            if(updated == null){
                throw new ApiException(404, "No item was updated");
            }
            ctx.json(updated);
        };
    }


    public Handler delete(){
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            if(id <= 0 || id > carDao.getAll().size()){
                throw new NumberFormatException();
            }
            Object deleted;
            if(onMemory){
                deleted = (CarDTO) carDao.delete(id);
            }else {
                deleted = (Car) carDao.delete(id);
                deleted = new CarDTO((Car) deleted);
            }

            ctx.json(deleted);
        };
    }

}
