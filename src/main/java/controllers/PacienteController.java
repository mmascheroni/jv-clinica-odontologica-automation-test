package controllers;

import config.ConfigProperties;
import exceptions.MissingPropertyException;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import models.Paciente;

import java.io.IOException;


import static io.restassured.RestAssured.given;

public class PacienteController {

    private ConfigProperties configProperties = new ConfigProperties();

    public PacienteController() throws MissingPropertyException, IOException {
    }

    private String getConfigProperty(String property, String propFileName) throws IOException, MissingPropertyException {
        configProperties.loadProperties(propFileName);

        return configProperties.getProp(property);
    }

    String baseUrl = getConfigProperty("BASE_URL", "config");



    public Response getPaciente(Long pacienteId, String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .get(baseUrl + "/pacientes/" + pacienteId);

        return res;
    }

    public Response getPacientes(String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .get(baseUrl + "/pacientes/");

        return res;
    }

    public Response postPaciente(Paciente paciente, String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .contentType("application/json")
                .body(paciente)
                .post(baseUrl + "/pacientes/registrar");

        return res;
    }

    public Response deletePaciente(Long pacienteId, String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .delete(baseUrl + "/pacientes/eliminar/" + pacienteId);

        return res;
    }


    public Paciente paciente(Long pacienteId, String credentials) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + credentials)
                .get(baseUrl + "/pacientes/" + pacienteId);

        Paciente paciente = res.as(Paciente.class);

        return paciente;
    }
}
