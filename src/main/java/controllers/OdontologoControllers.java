package controllers;

import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapper;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import models.Odontologo;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OdontologoControllers {

    String credentials = "admin@clinica.com:admin";
    String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());

    ObjectMapper objectMapper;

    public Odontologo getOdontologo(Long odontologoId) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .get("http://localhost:8082/odontologos/" + odontologoId);

        Odontologo odontologo = res.as(Odontologo.class);

        return odontologo;
    }


    public List<Odontologo> getOdontologos() {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .get("http://localhost:8082/odontologos/");


        List<Odontologo> odontologos = res.jsonPath().getList(".", Odontologo.class);


        return odontologos;
    }

    public Odontologo postOdontologo(Odontologo odontologo) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .contentType("application/json")
                .body(odontologo)
                .post("http://localhost:8082/odontologos/registrar");


        Odontologo odontologoRes = res.as(Odontologo.class);

        return odontologoRes;
    }



    public String deleteOdontologoById(Long odontologoId) {
        RestAssured.defaultParser = Parser.JSON;

        Response res = given()
                .header("Authorization", "Basic " + base64Credentials)
                .delete("http://localhost:8082/odontologos/eliminar/" + odontologoId);



        return res.prettyPrint();
    }
}
