package controllers;

import config.ConfigProperties;
import exceptions.MissingPropertyException;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import models.Odontologo;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OdontologoControllers {

    private ConfigProperties configProperties = new ConfigProperties();

    public OdontologoControllers() throws MissingPropertyException, IOException {
    }

//    public OdontologoControllers() throws MissingPropertyException, IOException {
//    }

    private String getConfigProperty(String property, String propFileName) throws IOException, MissingPropertyException {
        configProperties.loadProperties(propFileName);

        return configProperties.getProp(property);
    }

    String baseUrl = getConfigProperty("BASE_URL", "config");

    String userAdmin = getConfigProperty("USER_ADMIN", "config");

    String passAdmin = getConfigProperty("PASSWORD_ADMIN", "config");

    String credentials = userAdmin + ":" + passAdmin;
    String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());


    public Odontologo getOdontologo(Long odontologoId) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .get(baseUrl + "/odontologos/" + odontologoId);

        Odontologo odontologo = res.as(Odontologo.class);

        return odontologo;
    }


    public List<Odontologo> getOdontologos() {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .get(baseUrl + "/odontologos/");


        List<Odontologo> odontologos = res.jsonPath().getList(".", Odontologo.class);

        System.out.println(odontologos);


        return odontologos;
    }

    public Odontologo postOdontologo(Odontologo odontologo) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .contentType("application/json")
                .body(odontologo)
                .post(baseUrl + "/odontologos/registrar");

        System.out.println("Request: " + odontologo);


        Odontologo odontologoRes = res.as(Odontologo.class);

        System.out.println("RESPUESTA: " + odontologoRes);

        return odontologoRes;
    }



    public String deleteOdontologoById(Long odontologoId) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .delete(baseUrl + "/odontologos/eliminar/" + odontologoId);



        return res.prettyPrint();
    }
}
