package controllers;

import config.ConfigProperties;
import exceptions.MissingPropertyException;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import models.Paciente;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static io.restassured.RestAssured.given;

public class PacienteControllers {

    private ConfigProperties configProperties = new ConfigProperties();

    public PacienteControllers() throws MissingPropertyException, IOException {
    }

    private String getConfigProperty(String property, String propFileName) throws IOException, MissingPropertyException {
        configProperties.loadProperties(propFileName);

        return configProperties.getProp(property);
    }

    String baseUrl = getConfigProperty("BASE_URL", "config");

    String userAdmin = getConfigProperty("USER_ADMIN", "config");

    String passAdmin = getConfigProperty("PASSWORD_ADMIN", "config");

    String credentials = userAdmin + ":" + passAdmin;
    String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());


    public Paciente getPaciente(Long pacienteId) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .get(baseUrl + "/pacientes/" + pacienteId);

        Paciente paciente = res.as(Paciente.class);

        return paciente;
    }

    public List<Paciente> getPacientes() {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .get(baseUrl + "/pacientes/");

        List<Paciente> pacientes = res.jsonPath().getList(".", Paciente.class);

        return pacientes;
    }

    public Paciente postPaciente(Paciente paciente) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .contentType("application/json")
                .body(paciente)
                .post(baseUrl + "/pacientes/registrar");

        Paciente pacienteCreated = res.as(Paciente.class);

        return pacienteCreated;
    }

    public String deletePaciente(Long pacienteId) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .delete(baseUrl + "/pacientes/eliminar/" + pacienteId);

        return res.prettyPrint();
    }
}
