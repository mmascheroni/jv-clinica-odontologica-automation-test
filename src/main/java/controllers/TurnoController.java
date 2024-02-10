package controllers;

import config.ConfigProperties;
import exceptions.MissingPropertyException;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import models.Odontologo;
import models.Paciente;
import models.Turno;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TurnoController {

    private ConfigProperties configProperties = new ConfigProperties();

    public TurnoController() throws MissingPropertyException, IOException {
    }

    private String getConfigProperty(String property, String propFileName) throws IOException, MissingPropertyException {
        configProperties.loadProperties(propFileName);

        return configProperties.getProp(property);
    }

    String baseUrl = getConfigProperty("BASE_URL", "config");

    public Response getTurnos(String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .get(baseUrl + "/turnos/");

        return res;
    }

    public Response getTurno(Long turnoId, String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .get(baseUrl + "/odontologos/" + turnoId);


        return res;
    }

    public Response postTurno(Turno turno, Odontologo odontologo, Paciente paciente, String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .get(baseUrl + "/odontologos/registrar");


        return res;
    }

    public Response deleteTurno(Long turnoId, String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .get(baseUrl + "/turnos/" + turnoId);


        return res;
    }
}
