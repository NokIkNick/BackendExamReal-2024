package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;
import org.example.controllers.CarController;
import org.example.controllers.SecurityController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {
    private static SecurityController sc;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static CarController cC;


    public static EndpointGroup getCarRoutes(boolean isTesting, boolean onMemory){
       cC = CarController.getInstance(isTesting, onMemory);
        sc = SecurityController.getInstance(isTesting);
        return () -> {
            before(sc.authenticate());
            path("/", () -> {
                get("/", ctx -> ctx.json(objectMapper.createObjectNode().put("Message", "Connected Successfully")).header("Access-Control-Allow-Origin","*"), roles.ANYONE);
            });
            path("/auth", () -> {
                post("/login", sc.login(), roles.ANYONE);
                post("/register", sc.register(), roles.ANYONE);
            });
           path("/cars", () -> {
               get("/", cC.getAll(), roles.USER, roles.ADMIN);
               get("/{id}", cC.getById(), roles.USER, roles.ADMIN);
               post("/", cC.create(), roles.ADMIN);
               put("/{id}", cC.update(), roles.ADMIN);
               delete("/{id}", cC.delete(), roles.ADMIN);
           });
       };
    }


    public enum roles implements RouteRole {
        USER,
        ADMIN,
        ANYONE
    }

}
