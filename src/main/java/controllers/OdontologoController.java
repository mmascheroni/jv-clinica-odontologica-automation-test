package controllers;

import config.ConfigProperties;
import exceptions.MissingPropertyException;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import models.Odontologo;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class OdontologoController {

    private ConfigProperties configProperties = new ConfigProperties();

    public OdontologoController() throws MissingPropertyException, IOException {
    }

    private String getConfigProperty(String property, String propFileName) throws IOException, MissingPropertyException {
        configProperties.loadProperties(propFileName);

        return configProperties.getProp(property);
    }

    String baseUrl = getConfigProperty("BASE_URL", "config");


    public Response getOdontologo(Long odontologoId, String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .get(baseUrl + "/odontologos/" + odontologoId);


        return res;
    }


    public Response getOdontologos(String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .get(baseUrl + "/odontologos/");

        return res;
    }

    public Response postOdontologo(Odontologo odontologo, String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .contentType("application/json")
                .body(odontologo)
                .post(baseUrl + "/odontologos/registrar");


        return res;
    }

    public Response deleteOdontologoById(Long odontologoId, String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .delete(baseUrl + "/odontologos/eliminar/" + odontologoId);

        return res;
    }


    public Odontologo odontologo(Long odontologoId, String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .get(baseUrl + "/odontologos/" + odontologoId);

        Odontologo odontologo = res.as(Odontologo.class);

        return odontologo;
    }


    public Odontologo odontologoPost(Odontologo odontologo, String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .contentType("application/json")
                .body(odontologo)
                .post(baseUrl + "/odontologos/registrar");

        Odontologo odontologoCreated = res.as(Odontologo.class);

        return odontologoCreated;
    }
}
