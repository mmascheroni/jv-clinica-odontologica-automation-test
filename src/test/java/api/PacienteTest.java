package api;

import controllers.PacienteControllers;
import exceptions.MissingPropertyException;
import models.Domicilio;
import models.Paciente;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testng.Assert;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PacienteTest {

    LocalDate localDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String fechaIngresoString = localDate.format(formatter);

    Domicilio domicilio1 = new Domicilio("Calle Test", 2024, "Montevideo");

    Domicilio domicilio2 = new Domicilio("Calle Test 2", 2025, "Montevideo");

    Paciente pacienteNew1 = new Paciente("Paciente1", "Test1", "12345678", fechaIngresoString, domicilio1);
    Paciente pacienteNew2 = new Paciente("Paciente2", "Test2", "123456789", fechaIngresoString, domicilio2);


    @Test
    @Order(1)
    public void getPacientesHaveLengthEquals0() throws MissingPropertyException, IOException {
        PacienteControllers pacienteControllers = new PacienteControllers();

        List<Paciente> pacientes = pacienteControllers.getPacientes();

        Assert.assertTrue(pacientes.isEmpty(), "La lista de pacientes no está vacía");
    }

    @Test
    @Order(2)
    public void postPacienteWithIdEquals1() throws MissingPropertyException, IOException {
        PacienteControllers pacienteControllers = new PacienteControllers();

        Paciente paciente = pacienteControllers.postPaciente(pacienteNew1);

        Assert.assertEquals( paciente.getId(), 1);
        Assert.assertEquals( paciente.getNombre(), pacienteNew1.getNombre());
        Assert.assertEquals( paciente.getApellido(), pacienteNew1.getApellido());
        Assert.assertEquals( paciente.getDni(), pacienteNew1.getDni());
        Assert.assertEquals( paciente.getFechaIngreso(), pacienteNew1.getFechaIngreso());
        Assert.assertEquals( paciente.getDomicilio().getId(), 1);
        Assert.assertEquals( paciente.getDomicilio().getCalle(), domicilio1.getCalle());
        Assert.assertEquals( paciente.getDomicilio().getNumero(), domicilio1.getNumero());
    }

    @Test
    @Order(3)
    public void getPacienteWithIdEquals1() throws MissingPropertyException, IOException {
        PacienteControllers pacienteControllers = new PacienteControllers();

        Paciente paciente = pacienteControllers.getPaciente(1L);


        Assert.assertEquals(paciente.getId(), 1);
        Assert.assertEquals( paciente.getNombre(), pacienteNew1.getNombre());
        Assert.assertEquals( paciente.getApellido(), pacienteNew1.getApellido());
        Assert.assertEquals( paciente.getDni(), pacienteNew1.getDni());
        Assert.assertEquals( paciente.getFechaIngreso(), pacienteNew1.getFechaIngreso());
        Assert.assertEquals( paciente.getDomicilio().getId(), 1);
        Assert.assertEquals( paciente.getDomicilio().getCalle(), domicilio1.getCalle());
        Assert.assertEquals( paciente.getDomicilio().getNumero(), domicilio1.getNumero());
    }


    @Test
    @Order(4)
    public void getPacientesHaveLengthEquals1() throws MissingPropertyException, IOException {
        PacienteControllers pacienteControllers = new PacienteControllers();

        List<Paciente> pacientes = pacienteControllers.getPacientes();

        Assert.assertEquals(pacientes.size(), 1);
    }


    @Test
    @Order(5)
    public void postPacienteWithIdEquals2() throws MissingPropertyException, IOException {
        PacienteControllers pacienteControllers = new PacienteControllers();

        Paciente paciente = pacienteControllers.postPaciente(pacienteNew2);

        Assert.assertEquals( paciente.getId(), 2);
        Assert.assertEquals( paciente.getNombre(), pacienteNew2.getNombre());
        Assert.assertEquals( paciente.getApellido(), pacienteNew2.getApellido());
        Assert.assertEquals( paciente.getDni(), pacienteNew2.getDni());
        Assert.assertEquals( paciente.getFechaIngreso(), pacienteNew2.getFechaIngreso());
        Assert.assertEquals( paciente.getDomicilio().getId(), 2);
        Assert.assertEquals( paciente.getDomicilio().getCalle(), domicilio2.getCalle());
        Assert.assertEquals( paciente.getDomicilio().getNumero(), domicilio2.getNumero());
    }


    @Test
    @Order(6)
    public void getPacienteWithIdEquals2() throws MissingPropertyException, IOException {
        PacienteControllers pacienteControllers = new PacienteControllers();

        Paciente paciente = pacienteControllers.getPaciente(2L);


        Assert.assertEquals(paciente.getId(), 2);
        Assert.assertEquals( paciente.getNombre(), pacienteNew2.getNombre());
        Assert.assertEquals( paciente.getApellido(), pacienteNew2.getApellido());
        Assert.assertEquals( paciente.getDni(), pacienteNew2.getDni());
        Assert.assertEquals( paciente.getFechaIngreso(), pacienteNew2.getFechaIngreso());
        Assert.assertEquals( paciente.getDomicilio().getId(), 2);
        Assert.assertEquals( paciente.getDomicilio().getCalle(), domicilio2.getCalle());
        Assert.assertEquals( paciente.getDomicilio().getNumero(), domicilio2.getNumero());
    }


    @Test
    @Order(7)
    public void getPacientesHaveLengthEquals2() throws MissingPropertyException, IOException {
        PacienteControllers pacienteControllers = new PacienteControllers();

        List<Paciente> pacientes = pacienteControllers.getPacientes();

        Assert.assertEquals(pacientes.size(), 2);
    }

    @Test
    @Order(8)
    public void deletePacienteWithId1() throws MissingPropertyException, IOException {
        PacienteControllers pacienteControllers = new PacienteControllers();

        String res = pacienteControllers.deletePaciente(1L);

        Assert.assertEquals(res, "Paciente eliminado");
    }


    @Test
    @Order(9)
    public void getPacientesHaveLengthEquals1AfterDelete() throws MissingPropertyException, IOException {
        PacienteControllers pacienteControllers = new PacienteControllers();

        List<Paciente> pacientes = pacienteControllers.getPacientes();

        Assert.assertEquals(pacientes.size(), 1);
    }



}
