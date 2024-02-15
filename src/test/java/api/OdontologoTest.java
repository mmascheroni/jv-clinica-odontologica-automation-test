package api;

import config.ConfigProperties;
import config.UserCredentialsConfig;
import controllers.OdontologoController;
import exceptions.MissingPropertyException;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Odontologo;
import org.testng.annotations.Test;
import org.testng.Assert;


import java.io.IOException;
import java.util.List;

public class OdontologoTest {

    private ConfigProperties configProperties = new ConfigProperties();

    private UserCredentialsConfig userCredentialsConfig = new UserCredentialsConfig(configProperties);

    private String base64CredentialsAdmin = userCredentialsConfig.getUserBase64Credentials("USER_ADMIN", "PASSWORD_ADMIN", "config");

    private String base64CredentialsUser = userCredentialsConfig.getUserBase64Credentials("USER", "PASSWORD_USER", "config");

    Odontologo odontologoNew1 = new Odontologo("OdontologoUno", "OdontologoDos", "O001");

    Odontologo odontologoNew2 = new Odontologo("OdontologoDos", "TestDos", "O002");

    Odontologo odontologoWithoutName = new Odontologo("", "TestTres", "O003");

    Odontologo odontologoWithoutLastName = new Odontologo("OdontologoCuatro", "", "O004");

    Odontologo odontologoWithoutMatricula = new Odontologo("OdontologoCinco", "TestCinco", "");

    public OdontologoTest() throws MissingPropertyException, IOException {
    }

    // TESTS WITH USER ADMIN
    @Test(priority = 1)
    public void getOdontologosIsEmpty() throws MissingPropertyException, IOException {

        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.getOdontologos(base64CredentialsAdmin);

        List<Odontologo> odontologos = res.jsonPath().getList(".", Odontologo.class);

        Assert.assertTrue( odontologos.isEmpty(), "La lista de odontólogos no está vacía");
    }

    @Test(priority = 2)
    public void postOdontologoWithId1() throws MissingPropertyException, IOException {

        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.postOdontologo(odontologoNew1, base64CredentialsAdmin);

        Odontologo odontologo = res.as(Odontologo.class);

        Assert.assertEquals( odontologo.getId(), 1);
        Assert.assertEquals( odontologo.getNombre(), odontologoNew1.getNombre());
        Assert.assertEquals( odontologo.getApellido(), odontologoNew1.getApellido());
        Assert.assertEquals( odontologo.getMatricula(), odontologoNew1.getMatricula());
    }


    @Test(priority = 3)
    public void getOdontologoByIdEquals1() throws MissingPropertyException, IOException {

        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.getOdontologo(1L, base64CredentialsAdmin);

        Odontologo odontologo = res.as(Odontologo.class);

        Assert.assertEquals(odontologo.getId(), 1);
        Assert.assertEquals(odontologo.getNombre(), odontologoNew1.getNombre());
        Assert.assertEquals(odontologo.getApellido(), odontologoNew1.getApellido());
        Assert.assertEquals(odontologo.getMatricula(), odontologoNew1.getMatricula());
    }


    @Test(priority = 4)
    public void getOdontologosHaveLengthEquals1() throws MissingPropertyException, IOException {

        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.getOdontologos(base64CredentialsAdmin);

        List<Odontologo> odontologos = res.jsonPath().getList(".", Odontologo.class);

        Assert.assertEquals( odontologos.size(), 1);
    }

    @Test(priority = 5)
    public void postOdontologoWithId2() throws MissingPropertyException, IOException {

        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.postOdontologo(odontologoNew2, base64CredentialsAdmin);

        Odontologo odontologo = res.as(Odontologo.class);

        Assert.assertEquals( odontologo.getId(), 2);
        Assert.assertEquals( odontologo.getNombre(), odontologoNew2.getNombre());
        Assert.assertEquals( odontologo.getApellido(), odontologoNew2.getApellido());
        Assert.assertEquals( odontologo.getMatricula(), odontologoNew2.getMatricula());
    }


    @Test(priority = 6)
    public void getOdontologosHaveLengthEquals2() throws MissingPropertyException, IOException {

        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.getOdontologos(base64CredentialsAdmin);

        List<Odontologo> odontologos = res.jsonPath().getList(".", Odontologo.class);

        Assert.assertEquals( odontologos.size(), 2);
    }

    @Test(priority = 7)
    public void deleteOdontologoWithId1() throws MissingPropertyException, IOException {

        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.deleteOdontologoById(1L, base64CredentialsAdmin);

        Assert.assertEquals( res.prettyPrint(), "Odontologo eliminado");
    }

    @Test(priority = 8)
    public void getOdontologosHaveLengthEquals1AfterDelete() throws MissingPropertyException, IOException {

        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.getOdontologos(base64CredentialsAdmin);

        List<Odontologo> odontologos = res.jsonPath().getList(".", Odontologo.class);

        Assert.assertEquals( odontologos.size(), 1);
    }


    // TESTS WITH USER
    @Test(priority = 9)
    public void shouldNotPostToOdontologo() throws MissingPropertyException, IOException {
        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.postOdontologo(odontologoNew2, base64CredentialsUser);

        Assert.assertEquals(res.getStatusCode(), 403);
    }

    @Test(priority = 10)
    public void shouldNotGetOdontologos() throws MissingPropertyException, IOException {
        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.getOdontologos(base64CredentialsUser);

        Assert.assertEquals(res.getStatusCode(), 403);
    }

    @Test(priority = 11)
    public void shouldNotGetOdontologo() throws MissingPropertyException, IOException {
        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.getOdontologo(1L, base64CredentialsUser);

        Assert.assertEquals(res.getStatusCode(), 403);
    }

    @Test(priority = 12)
    public void shouldNotDeleteOdontologo() throws MissingPropertyException, IOException {
        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.deleteOdontologoById(1L, base64CredentialsUser);

        Assert.assertEquals(res.getStatusCode(), 403);
    }


    // TEST WITHOUT DATA ON BODY
    @Test(priority = 13)
    public void shouldMessageNameEmptyToPostOdontologo() throws MissingPropertyException, IOException {
        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.postOdontologo(odontologoWithoutName, base64CredentialsAdmin);

        String resBody = res.getBody().asString();

        JsonPath jsonPathRes = new JsonPath(resBody);
        String messageError = jsonPathRes.getString("nombre");

        Assert.assertEquals(messageError, "Debe especificarse el nombre del odontologo, no puede quedar vacio");
    }


    @Test(priority = 14)
    public void shouldMessageLastNameEmptyToPostOdontologo() throws MissingPropertyException, IOException {
        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.postOdontologo(odontologoWithoutLastName, base64CredentialsAdmin);

        String resBody = res.getBody().asString();

        JsonPath jsonPathRes = new JsonPath(resBody);
        String messageError = jsonPathRes.getString("apellido");

        Assert.assertEquals(messageError, "Debe especificarse el apellido del odontologo, no puede quedar vacio");
    }

    @Test(priority = 15)
    public void shouldMessageMatriculaEmptyToPostOdontologo() throws MissingPropertyException, IOException {
        OdontologoController odontologoController = new OdontologoController();

        Response res = odontologoController.postOdontologo(odontologoWithoutMatricula, base64CredentialsAdmin);

        String resBody = res.getBody().asString();

        JsonPath jsonPathRes = new JsonPath(resBody);
        String messageError = jsonPathRes.getString("matricula");

        Assert.assertEquals(messageError, "Debe especificarse la matricula del odontologo, no puede quedar vacia");
    }
}
