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

    private LocalDateAdapter localDateAdapter = new LocalDateAdapter();

    String fechaIngresoString = localDateAdapter.localDateToString(LocalDate.now(), "dd-MM-YYYY");

    String fechaIngresoStringToAsserts = localDateAdapter.localDateToString(LocalDate.now(), "YYYY-MM-dd");

    Paciente pacienteNew1 = new Paciente("PacienteUno", "PacienteDos", "1234567", fechaIngresoString, domicilio);


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
        Assert.assertEquals(pacienteNumber, pacienteNew1.getDomicilio().getNumero());
        Assert.assertEquals(pacienteLocation, pacienteNew1.getDomicilio().getLocalidad());
    }



//    @AfterClass
//    public void tearDown() {
//        PacientePage pacientePage = new PacientePage(driver, null);
//
//        pacientePage.close();
//    }

}
