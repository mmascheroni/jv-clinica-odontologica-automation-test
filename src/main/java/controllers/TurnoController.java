package controllers;

import config.ConfigProperties;
import exceptions.MissingPropertyException;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Odontologo;
import models.Paciente;
import models.Turno;
import org.json.simple.JSONObject;

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
                .get(baseUrl + "/turnos/" + turnoId);


        return res;
    }

    public Response postTurno(Turno turno, String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .contentType("application/json")
                .body(turno)
                .post(baseUrl + "/turnos/registrar");

        return res;
    }

    public Response deleteTurno(Long turnoId, String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .delete(baseUrl + "/turnos/eliminar/" + turnoId);


        return res;
    }
}
