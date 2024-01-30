package api;

import controllers.OdontologoControllers;
import models.Odontologo;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testng.Assert;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.util.List;

@TestMethodOrder(OrderAnnotation.class)
public class OdontologoTest {

    // TESTS WITH USER ADMIN

    @Test()
    @Order(1)
    public void getOdontologosIsEmpty() {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        List<Odontologo> odontologos = odontologoControllers.getOdontologos();


        Assert.assertTrue( odontologos.isEmpty(), "La lista de odontólogos no está vacía");
    }

    @Test
    @Order(2)
    public void postOdontologoWithId1() {

        Odontologo odontologoNew = new Odontologo("Mauro", "Mascheroni", "O001");

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Odontologo odontologo = odontologoControllers.postOdontologo(odontologoNew);

        Assert.assertEquals( odontologo.getId(), 1);
        Assert.assertEquals( odontologo.getNombre(), odontologoNew.getNombre());
        Assert.assertEquals( odontologo.getApellido(), odontologoNew.getApellido());
        Assert.assertEquals( odontologo.getMatricula(), odontologoNew.getMatricula());

//        Odontologo getOdontologoCreated = odontologoControllers.getOdontologo(3L);
//        Assert.assertEquals( getOdontologoCreated.getNombre(), odontologoNew.getNombre());
    }


    @Test
    @Order(3)
    public void getOdontologoByIdEquals1() {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Odontologo odontologo = odontologoControllers.getOdontologo(1L);

        System.out.println(odontologo);

        Assert.assertEquals(odontologo.getId(), 1);
        Assert.assertEquals(odontologo.getNombre(), "Mauro");
        Assert.assertEquals(odontologo.getApellido(), "Mascheroni");
        Assert.assertEquals(odontologo.getMatricula(), "O001");
    }


    @Test
    @Order(4)
    public void getOdontologosHaveLengthEquals1() {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        List<Odontologo> odontologos = odontologoControllers.getOdontologos();

        Assert.assertEquals( odontologos.size(), 1);
    }

    @Test
    @Order(5)
    public void postOdontologoWithId2() {

        Odontologo odontologoNew = new Odontologo("OdontologoName", "Test2", "O002");

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        Odontologo odontologo = odontologoControllers.postOdontologo(odontologoNew);

        Assert.assertEquals( odontologo.getId(), 2);
        Assert.assertEquals( odontologo.getNombre(), odontologoNew.getNombre());
        Assert.assertEquals( odontologo.getApellido(), odontologoNew.getApellido());
        Assert.assertEquals( odontologo.getMatricula(), odontologoNew.getMatricula());

//        Odontologo getOdontologoCreated = odontologoControllers.getOdontologo(3L);
//        Assert.assertEquals( getOdontologoCreated.getNombre(), odontologoNew.getNombre());
    }


    @Test
    @Order(6)
    public void getOdontologosHaveLengthEquals2() {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        List<Odontologo> odontologos = odontologoControllers.getOdontologos();

        Assert.assertEquals( odontologos.size(), 2);
    }

    @Test
    @Order(7)
    public void deleteOdontologoWithId1() {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        String res = odontologoControllers.deleteOdontologoById(1L);

        Assert.assertEquals( res, "Odontologo eliminado");
    }

    @Test
    @Order(8)
    public void getOdontologosHaveLengthEquals1AfterDelete() {

        OdontologoControllers odontologoControllers = new OdontologoControllers();

        List<Odontologo> odontologos = odontologoControllers.getOdontologos();

        Assert.assertEquals( odontologos.size(), 1);
    }
}
