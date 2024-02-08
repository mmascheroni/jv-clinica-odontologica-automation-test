package controllers;

import config.ConfigProperties;
import exceptions.MissingPropertyException;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import models.Odontologo;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OdontologoControllers {

    private ConfigProperties configProperties = new ConfigProperties();

    public OdontologoControllers() throws MissingPropertyException, IOException {
    }

    private String getConfigProperty(String property, String propFileName) throws IOException, MissingPropertyException {
        configProperties.loadProperties(propFileName);

        return configProperties.getProp(property);
    }

    String baseUrl = getConfigProperty("BASE_URL", "config");


    // ADMIN
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


}
