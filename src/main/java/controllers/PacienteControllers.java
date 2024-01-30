package controllers;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import models.Paciente;

import java.util.Base64;
import java.util.List;

import static io.restassured.RestAssured.given;

public class PacienteControllers {

    String credentials = "admin@clinica.com:admin";
    String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());


    public Paciente getPaciente(Long pacienteId) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .get("http://localhost:8082/pacientes/" + pacienteId);

        Paciente paciente = res.as(Paciente.class);

        return paciente;
    }

    public List<Paciente> getPacientes() {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .get("http://localhost:8082/pacientes/");

        List<Paciente> pacientes = res.jsonPath().getList(".", Paciente.class);

        return pacientes;
    }

    public Paciente postPaciente(Paciente paciente) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .contentType("application/json")
                .body(paciente)
                .post("http://localhost:8082/pacientes/registrar");

        Paciente pacienteCreated = res.as(Paciente.class);

        return pacienteCreated;
    }

    public String deletePaciente(Long pacienteId) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .delete("http://localhost:8082/pacientes/eliminar/" + pacienteId);

        return res.prettyPrint();
    }
}
