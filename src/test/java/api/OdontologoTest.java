package api;

import controllers.OdontologoControllers;
import exceptions.MissingPropertyException;
import models.Odontologo;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testng.Assert;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.IOException;
import java.util.List;

@TestMethodOrder(OrderAnnotation.class)
public class OdontologoTest {

    Odontologo odontologoNew1 = new Odontologo("Mauro", "Mascheroni", "O001");

    Odontologo odontologoNew2 = new Odontologo("OdontologoName", "Test2", "O002");

    // TESTS WITH USER ADMIN

    @Test()
    @Order(1)
    public void getOdontologosIsEmpty() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        List<Odontologo> odontologos = odontologoControllers.getOdontologos();


        Assert.assertTrue( odontologos.isEmpty(), "La lista de odontólogos no está vacía");
    }

    @Test
    @Order(2)
    public void postOdontologoWithId1() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Odontologo odontologo = odontologoControllers.postOdontologo(odontologoNew1);

        Assert.assertEquals( odontologo.getId(), 1);
        Assert.assertEquals( odontologo.getNombre(), odontologoNew1.getNombre());
        Assert.assertEquals( odontologo.getApellido(), odontologoNew1.getApellido());
        Assert.assertEquals( odontologo.getMatricula(), odontologoNew1.getMatricula());

//        Odontologo getOdontologoCreated = odontologoControllers.getOdontologo(3L);
//        Assert.assertEquals( getOdontologoCreated.getNombre(), odontologoNew.getNombre());
    }


    @Test
    @Order(3)
    public void getOdontologoByIdEquals1() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Odontologo odontologo = odontologoControllers.getOdontologo(1L);

        System.out.println(odontologo);

        Assert.assertEquals(odontologo.getId(), 1);
        Assert.assertEquals(odontologo.getNombre(), odontologoNew1.getNombre());
        Assert.assertEquals(odontologo.getApellido(), odontologoNew1.getApellido());
        Assert.assertEquals(odontologo.getMatricula(), odontologoNew1.getMatricula());
    }


    @Test
    @Order(4)
    public void getOdontologosHaveLengthEquals1() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        List<Odontologo> odontologos = odontologoControllers.getOdontologos();

        Assert.assertEquals( odontologos.size(), 1);
    }

    @Test
    @Order(5)
    public void postOdontologoWithId2() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Odontologo odontologo = odontologoControllers.postOdontologo(odontologoNew2);

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

        List<Odontologo> odontologos = odontologoControllers.getOdontologos();

        Assert.assertEquals( odontologos.size(), 2);
    }

    @Test
    @Order(7)
    public void deleteOdontologoWithId1() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        String res = odontologoControllers.deleteOdontologoById(1L);

        Assert.assertEquals( res, "Odontologo eliminado");
    }

    @Test
    @Order(8)
    public void getOdontologosHaveLengthEquals1AfterDelete() throws MissingPropertyException, IOException {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        List<Odontologo> odontologos = odontologoControllers.getOdontologos();

        Assert.assertEquals( odontologos.size(), 1);
    }
}
