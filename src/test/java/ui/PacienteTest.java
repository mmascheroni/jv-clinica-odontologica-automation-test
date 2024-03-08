package ui;

import config.ConfigProperties;
import exceptions.MissingPropertyException;
import models.Domicilio;
import models.Paciente;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.PacientePage;
import utils.LocalDateAdapter;

import java.io.IOException;
import java.time.LocalDate;

import static java.lang.Integer.parseInt;

public class PacienteTest {

    public WebDriver driver;

    private ConfigProperties configProperties = new ConfigProperties();

    private String getConfigProperty(String property, String propFileName) throws IOException, MissingPropertyException {
        configProperties.loadProperties(propFileName);

        return configProperties.getProp(property);
    }

    Domicilio domicilio = new Domicilio("calle Test", 1234, "localidad Test");

    Domicilio domicilioToModify = new Domicilio("calle Modify Test", 1111, "localidad Modify Test");

    private LocalDateAdapter localDateAdapter = new LocalDateAdapter();

    String fechaIngresoString = localDateAdapter.localDateToString(LocalDate.now(), "dd-MM-YYYY");

    String fechaIngresoStringToAsserts = localDateAdapter.localDateToString(LocalDate.now(), "YYYY-MM-dd");

    Paciente pacienteNew1 = new Paciente("PacienteUno", "TestUno", "1234567", fechaIngresoString, domicilio);

    Paciente pacienteToModify1 = new Paciente("PacienteModifyUno", "TestModifyUno", "1234666", fechaIngresoString, domicilioToModify);

    Paciente pacienteNew2 = new Paciente("PacienteDos", "TestDos", "1234568", fechaIngresoString, domicilio);

    @BeforeClass
    public void preconditions() throws MissingPropertyException, IOException {
        driver = new ChromeDriver();

        PacientePage pacientePage = new PacientePage(driver, null);
        pacientePage.setup();

        pacientePage.navigateToPaciente(getConfigProperty("USER_ADMIN", "config"), getConfigProperty("PASSWORD_ADMIN", "config"));
    }


    @Test(priority = 1)
    public void pacienteTableIsEmpty() {
        PacientePage pacientePage = new PacientePage(driver, null);

        String messageNoPacientesInTable = pacientePage.pacienteTableIsEmpty();

        Assert.assertEquals(messageNoPacientesInTable, "No hay registros actualmente...");
    }


    @Test(priority = 2)
    public void addPacienteWithId1() {
        PacientePage pacientePage = new PacientePage(driver, null);

        pacientePage.navigateToFormToAddPaciente();
        pacientePage.insertName(pacienteNew1.getNombre());
        pacientePage.insertLastName(pacienteNew1.getApellido());
        pacientePage.insertDNI(pacienteNew1.getDni());
        pacientePage.insertIncomeDate(pacienteNew1.getFechaIngreso());
        pacientePage.insertStreet(pacienteNew1.getDomicilio().getCalle());
        pacientePage.insertNumber(pacienteNew1.getDomicilio().getNumero());
        pacientePage.insertLocation(pacienteNew1.getDomicilio().getLocalidad());
        pacientePage.addPaciente();

        String pacienteId = pacientePage.getPacienteAddedIntoTable(1, 1);
        String pacienteName = pacientePage.getPacienteAddedIntoTable(1, 2);
        String pacienteLastName = pacientePage.getPacienteAddedIntoTable(1, 3);
        String pacienteDNI = pacientePage.getPacienteAddedIntoTable(1, 4);
        String pacienteIncomeDate = pacientePage.getPacienteAddedIntoTable(1, 5);
        String pacienteStreet = pacientePage.getPacienteAddedIntoTable(1, 7);
        String pacienteNumber = pacientePage.getPacienteAddedIntoTable(1, 8);
        String pacienteLocation = pacientePage.getPacienteAddedIntoTable(1, 9);

        Assert.assertEquals(pacientePage.tableRows().size(), 1);
        Assert.assertEquals(parseInt(pacienteId), 1);
        Assert.assertEquals(pacienteName, pacienteNew1.getNombre());
        Assert.assertEquals(pacienteLastName, pacienteNew1.getApellido());
        Assert.assertEquals(pacienteDNI, pacienteNew1.getDni());
        Assert.assertEquals(pacienteIncomeDate, fechaIngresoStringToAsserts);
        Assert.assertEquals(pacienteStreet, pacienteNew1.getDomicilio().getCalle());
        Assert.assertEquals(parseInt(pacienteNumber), pacienteNew1.getDomicilio().getNumero());
        Assert.assertEquals(pacienteLocation, pacienteNew1.getDomicilio().getLocalidad());
    }

    @Test(priority = 3)
    public void modifyPacienteWithId1() {
        PacientePage pacientePage = new PacientePage(driver, null);

        pacientePage.navigateToFormToModifiyPaciente();
        pacientePage.modifyInsertName(pacienteToModify1.getNombre());
        pacientePage.modifyInsertLastName(pacienteToModify1.getApellido());
        pacientePage.modifyInsertDNI(pacienteToModify1.getDni());
        pacientePage.modifyInsertIncomeDate(pacienteToModify1.getFechaIngreso());
        pacientePage.modifyInsertStreet(pacienteToModify1.getDomicilio().getCalle());
        pacientePage.modifyInsertNumber(pacienteToModify1.getDomicilio().getNumero());
        pacientePage.modifyInsertLocation(pacienteToModify1.getDomicilio().getLocalidad());
        pacientePage.modifiyPaciente();

        String pacienteId = pacientePage.getFirstIdOnTable();
        String pacienteName = pacientePage.getPacienteAddedIntoTable(1, 2);
        String pacienteLastName = pacientePage.getPacienteAddedIntoTable(1, 3);
        String pacienteDNI = pacientePage.getPacienteAddedIntoTable(1, 4);
        String pacienteIncomeDate = pacientePage.getPacienteAddedIntoTable(1, 5);
        String pacienteStreet = pacientePage.getPacienteAddedIntoTable(1, 7);
        String pacienteNumber = pacientePage.getPacienteAddedIntoTable(1, 8);
        String pacienteLocation = pacientePage.getPacienteAddedIntoTable(1, 9);

        Assert.assertEquals(pacientePage.tableRows().size(), 1);
        Assert.assertEquals(parseInt(pacienteId), 1);
        Assert.assertEquals(pacienteName, pacienteToModify1.getNombre());
        Assert.assertEquals(pacienteLastName, pacienteToModify1.getApellido());
        Assert.assertEquals(pacienteDNI, pacienteToModify1.getDni());
        Assert.assertEquals(pacienteIncomeDate, fechaIngresoStringToAsserts);
        Assert.assertEquals(pacienteStreet, pacienteToModify1.getDomicilio().getCalle());
        Assert.assertEquals(parseInt(pacienteNumber), pacienteToModify1.getDomicilio().getNumero());
        Assert.assertEquals(pacienteLocation, pacienteToModify1.getDomicilio().getLocalidad());
    }



    @Test(priority = 4)
    public void addPacienteWithId2() {
        PacientePage pacientePage = new PacientePage(driver, null);

        pacientePage.navigateToFormToAddPaciente();
        pacientePage.insertName(pacienteNew2.getNombre());
        pacientePage.insertLastName(pacienteNew2.getApellido());
        pacientePage.insertDNI(pacienteNew2.getDni());
        pacientePage.insertIncomeDate(pacienteNew2.getFechaIngreso());
        pacientePage.insertStreet(pacienteNew2.getDomicilio().getCalle());
        pacientePage.insertNumber(pacienteNew2.getDomicilio().getNumero());
        pacientePage.insertLocation(pacienteNew2.getDomicilio().getLocalidad());
        pacientePage.addPaciente();

        String pacienteId = pacientePage.getPacienteAddedIntoTable(2, 1);
        String pacienteName = pacientePage.getPacienteAddedIntoTable(2, 2);
        String pacienteLastName = pacientePage.getPacienteAddedIntoTable(2, 3);
        String pacienteDNI = pacientePage.getPacienteAddedIntoTable(2, 4);
        String pacienteIncomeDate = pacientePage.getPacienteAddedIntoTable(2, 5);
        String pacienteStreet = pacientePage.getPacienteAddedIntoTable(2, 7);
        String pacienteNumber = pacientePage.getPacienteAddedIntoTable(2, 8);
        String pacienteLocation = pacientePage.getPacienteAddedIntoTable(2, 9);

        Assert.assertEquals(pacientePage.tableRows().size(), 2);
        Assert.assertEquals(parseInt(pacienteId), 2);
        Assert.assertEquals(pacienteName, pacienteNew2.getNombre());
        Assert.assertEquals(pacienteLastName, pacienteNew2.getApellido());
        Assert.assertEquals(pacienteDNI, pacienteNew2.getDni());
        Assert.assertEquals(pacienteIncomeDate, fechaIngresoStringToAsserts);
        Assert.assertEquals(pacienteStreet, pacienteNew2.getDomicilio().getCalle());
        Assert.assertEquals(parseInt(pacienteNumber), pacienteNew2.getDomicilio().getNumero());
        Assert.assertEquals(pacienteLocation, pacienteNew2.getDomicilio().getLocalidad());
    }



//    @AfterClass
//    public void tearDown() {
//        PacientePage pacientePage = new PacientePage(driver, null);
//
//        pacientePage.close();
//    }

}
