package org.example.controllers;

import io.javalin.http.HttpStatus;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.config.ApplicationConfig;
import org.example.config.HibernateConfig;
import org.example.config.Routes;
import org.example.model.Role;
import org.example.model.User;
import org.example.utils.Populator;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

class CarControllerTest {

    private static EntityManagerFactory emf;
    private static Role user, admin;
    private static Object adminToken;
    private static Object userToken;
    private static User userUser, userAdmin;
    private static final boolean isTesting = true;

    @BeforeAll
    static void setupAll(){
        emf = HibernateConfig.getEntityManagerFactoryConfigForTesting();
        RestAssured.baseURI = "http://localhost:7778/api";
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig.initiateServer().startServer(7778).setExceptionHandling().setRoutes(Routes.getCarRoutes(isTesting, false)).checkSecurityRoles(isTesting);

        user = new Role("USER");
        admin = new Role("ADMIN");

        userUser = new User("TestUser", "testPasswordUser", user);
        userAdmin = new User("TestAdmin", "testPasswordAdmin", admin);

        try(var em = emf.createEntityManager()){
           em.getTransaction().begin();
           em.persist(user);
           em.persist(admin);
           em.persist(userUser);
           em.persist(userAdmin);
           em.getTransaction().commit();

            Populator.populate(true);
        }





    }

    @BeforeEach
    void setUp(){
        try(var em = emf.createEntityManager()){
            adminToken = getToken(userAdmin.getUsername(), "testPasswordAdmin");
            userToken = getToken(userUser.getUsername(), "testPasswordUser");
            em.clear();
        }
    }

    @AfterAll
    static void cleanUp(){
        try(var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Seller").executeUpdate();
            em.createQuery("DELETE FROM Car").executeUpdate();
            em.getTransaction().commit();
        }
    }


    @Test
    void getAll() {
        given().header("Authorization", userToken)
                .when()
                .get("http://localhost:7778/api/cars")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.OK.getCode())
                .body("empty",equalTo(false)).body("Content-Length", not(0));
    }




    @Test
    void getById() {
        RestAssured.given().header("Authorization", userToken)
                .when()
                .get("http://localhost:7778/api/cars/1")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.OK.getCode())
                .body("brand", equalTo("Citroen")).body("id",equalTo(1)).body("model",equalTo("Cactus 5"));
    }

    @Test
    void create() {

        String json = "{\n" +
                "    \"brand\":\"Volkswagen\",\n" +
                "    \"model\":\"Polo\",\n" +
                "    \"make\":\"Volkswagen\",\n" +
                "    \"year\":2002,\n" +
                "    \"firstRegistrationDate\":\"2005-09-11\",\n" +
                "    \"price\":120.000\n" +
                "}";

        RestAssured.given().header("Authorization", adminToken)
                .contentType("application/json")
                .body(json)
                .when()
                .post("http://localhost:7778/api/cars")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.OK.getCode())
                .body("brand",equalTo("Volkswagen")).body("id",equalTo(6)).body("price",equalTo(120.0F));
    }

    @Test
    void update() {
        String json = "{\n" +
                "  \"brand\":\"Citroen\",\n" +
                "  \"model\":\"Cactus 5\",\n" +
                "  \"make\":\"Citroen\",\n" +
                "  \"year\":2010,\n" +
                "  \"firstRegistrationDate\":\"2012-08-07\",\n" +
                "  \"price\":175.000\n" +
                "}";

        RestAssured.given()
                .contentType("application/json").header("Authorization", adminToken)
                .body(json)
                .when()
                .put("http://localhost:7778/api/cars/1")
                .then()
                .log()
                .all()
                .assertThat()
                .body("brand", equalTo("Citroen")).body("id", equalTo(1));

    }

    public static Object getToken(String username, String password)
    {
        return login(username, password);
    }

    private static Object login(String username, String password)
    {
        String json = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        var token = given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("http://localhost:7778/api/auth/login")
                .then()
                .extract()
                .response()
                .body()
                .path("token");

        return "Bearer " + token;
    }
}