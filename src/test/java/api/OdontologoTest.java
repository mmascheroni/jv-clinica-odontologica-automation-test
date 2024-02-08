package api;

import config.ConfigProperties;
import config.UserCredentialsConfig;
import controllers.OdontologoControllers;
import exceptions.MissingPropertyException;
import io.restassured.response.Response;
import models.Odontologo;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testng.Assert;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@TestMethodOrder(OrderAnnotation.class)
public class OdontologoTest {

    private ConfigProperties configProperties = new ConfigProperties();

    private UserCredentialsConfig userCredentialsConfig = new UserCredentialsConfig(configProperties);

    private String base64CredentialsAdmin = userCredentialsConfig.getUserBase64Credentials("USER_ADMIN", "PASSWORD_ADMIN", "config");

    private String base64CredentialsUser = userCredentialsConfig.getUserBase64Credentials("USER", "PASSWORD_USER", "config");

    Odontologo odontologoNew1 = new Odontologo("OdontologoUno", "OdontologoDos", "O001");

    Odontologo odontologoNew2 = new Odontologo("OdontologoDos", "TestDos", "O002");

    public OdontologoTest() throws MissingPropertyException, IOException {
    }

    // TESTS WITH USER ADMIN
    @Test()
    @Order(1)
    public void getOdontologosIsEmpty() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Response res = odontologoControllers.getOdontologos(base64CredentialsAdmin);

        List<Odontologo> odontologos = res.jsonPath().getList(".", Odontologo.class);

        Assert.assertTrue( odontologos.isEmpty(), "La lista de odontólogos no está vacía");
    }

    @Test
    @Order(2)
    public void postOdontologoWithId1() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Response res = odontologoControllers.postOdontologo(odontologoNew1, base64CredentialsAdmin);

        Odontologo odontologo = res.as(Odontologo.class);

        Assert.assertEquals( odontologo.getId(), 1);
        Assert.assertEquals( odontologo.getNombre(), odontologoNew1.getNombre());
        Assert.assertEquals( odontologo.getApellido(), odontologoNew1.getApellido());
        Assert.assertEquals( odontologo.getMatricula(), odontologoNew1.getMatricula());
    }


    @Test
    @Order(3)
    public void getOdontologoByIdEquals1() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Response res = odontologoControllers.getOdontologo(1L, base64CredentialsAdmin);

        Odontologo odontologo = res.as(Odontologo.class);

        Assert.assertEquals(odontologo.getId(), 1);
        Assert.assertEquals(odontologo.getNombre(), odontologoNew1.getNombre());
        Assert.assertEquals(odontologo.getApellido(), odontologoNew1.getApellido());
        Assert.assertEquals(odontologo.getMatricula(), odontologoNew1.getMatricula());
    }


    @Test
    @Order(4)
    public void getOdontologosHaveLengthEquals1() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Response res = odontologoControllers.getOdontologos(base64CredentialsAdmin);

        List<Odontologo> odontologos = res.jsonPath().getList(".", Odontologo.class);

        Assert.assertEquals( odontologos.size(), 1);
    }

    @Test
    @Order(5)
    public void postOdontologoWithId2() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Response res = odontologoControllers.postOdontologo(odontologoNew2, base64CredentialsAdmin);

        Odontologo odontologo = res.as(Odontologo.class);

        Assert.assertEquals( odontologo.getId(), 2);
        Assert.assertEquals( odontologo.getNombre(), odontologoNew2.getNombre());
        Assert.assertEquals( odontologo.getApellido(), odontologoNew2.getApellido());
        Assert.assertEquals( odontologo.getMatricula(), odontologoNew2.getMatricula());

//        Odontologo getOdontologoCreated = odontologoControllers.getOdontologo(3L);
//        Assert.assertEquals( getOdontologoCreated.getNombre(), odontologoNew.getNombre());
    }


    @Test
    @Order(6)
    public void getOdontologosHaveLengthEquals2() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Response res = odontologoControllers.getOdontologos(base64CredentialsAdmin);

        List<Odontologo> odontologos = res.jsonPath().getList(".", Odontologo.class);

        Assert.assertEquals( odontologos.size(), 2);
    }

    @Test
    @Order(7)
    public void deleteOdontologoWithId1() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Response res = odontologoControllers.deleteOdontologoById(1L, base64CredentialsAdmin);

        Assert.assertEquals( res.prettyPrint(), "Odontologo eliminado");
    }

    @Test
    @Order(8)
    public void getOdontologosHaveLengthEquals1AfterDelete() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Response res = odontologoControllers.getOdontologos(base64CredentialsAdmin);

        List<Odontologo> odontologos = res.jsonPath().getList(".", Odontologo.class);

        Assert.assertEquals( odontologos.size(), 1);
    }


    // TESTS WITH USER
    @Test
    @Order(9)
    public void shouldNotPostToOdontologo() throws MissingPropertyException, IOException {
        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Response res = odontologoControllers.postOdontologo(odontologoNew2, base64CredentialsUser);

        Assert.assertEquals(res.getStatusCode(), 403);
    }
}
