package ui;

import config.ConfigProperties;
import exceptions.MissingPropertyException;
import models.Odontologo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.OdontologoPage;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class OdontologoTest {

    public WebDriver driver;

    private ConfigProperties configProperties = new ConfigProperties();

    private Odontologo odontologo1 = new Odontologo("OdontologoUno", "testUno", "ODO001");

    private Odontologo odontologo2 = new Odontologo("OdontologoTres", "testTres", "ODO003");

    private Odontologo odontologo3 = new Odontologo("OdontologoTres", "testTres", "ODO003");

    private String getConfigProperty(String property, String propFileName) throws IOException, MissingPropertyException {
        configProperties.loadProperties(propFileName);

        return configProperties.getProp(property);
    }

    @BeforeClass
    public void preconditions() throws MissingPropertyException, IOException {
        driver = new ChromeDriver();

        OdontologoPage odontologoPage = new OdontologoPage(driver, null);
        odontologoPage.setup();



        odontologoPage.navigateToOdontologo(getConfigProperty("USER_ADMIN", "config"), getConfigProperty("PASSWORD_ADMIN", "config"));
    }

    @Test(priority = 1)
    public void addOdontologoWithId1() {
        OdontologoPage odontologoPage = new OdontologoPage(driver, null);

        odontologoPage.navigateToFormToAddOdontologo();
        odontologoPage.insertName(odontologo1.getNombre());
        odontologoPage.insertLastName(odontologo1.getApellido());
        odontologoPage.insertMatricula(odontologo1.getMatricula());
        odontologoPage.clickBtnFormAddOdontologo();


        String odontologoId = odontologoPage.getOdontologoAddedIntoTable(1, 1);
        String odontologoName = odontologoPage.getOdontologoAddedIntoTable(1, 2);
        String odontologoLastName = odontologoPage.getOdontologoAddedIntoTable(1, 3);
        String odontologoMatricula = odontologoPage.getOdontologoAddedIntoTable(1, 4);


        Assert.assertEquals(odontologoPage.tableRows().size(), 1);
        Assert.assertEquals(parseInt(odontologoId), 1);
        Assert.assertEquals(odontologoName, odontologo1.getNombre());
        Assert.assertEquals(odontologoLastName, odontologo1.getApellido());
        Assert.assertEquals(odontologoMatricula, odontologo1.getMatricula());

    }

    @Test(priority = 2)
    public void deleteFirstOdontologoInTable() {
        OdontologoPage odontologoPage = new OdontologoPage(driver, null);
        odontologoPage.clickBtnDeleteFirstOdontologoInTable();
        odontologoPage.acceptAlertToDeleteOdontologo();

        String emptyTable = odontologoPage.odontologoTableIsEmpty();

        Assert.assertEquals(emptyTable, "No hay registros actualmente...");

    }

    @Test(priority = 3)
    public void addOdontologoWithId2() {
        OdontologoPage odontologoPage = new OdontologoPage(driver, null);

        odontologoPage.navigateToFormToAddOdontologo();
        odontologoPage.insertName(odontologo2.getNombre());
        odontologoPage.insertLastName(odontologo2.getApellido());
        odontologoPage.insertMatricula(odontologo2.getMatricula());
        odontologoPage.clickBtnFormAddOdontologo();

        String odontologoId = odontologoPage.getIdInTableFirstRow();
        String odontologoName = odontologoPage.getNameInTableFirstRow();
        String odontologoLastName = odontologoPage.getLastNameInTableFirstRow();
        String odontologoMatricula = odontologoPage.getMatriculaInTableFirstRow();

        Assert.assertEquals(parseInt(odontologoId), 2);
        Assert.assertEquals(odontologoName, odontologo2.getNombre());
        Assert.assertEquals(odontologoLastName, odontologo2.getApellido());
        Assert.assertEquals(odontologoMatricula, odontologo2.getMatricula());
    }

    @Test(priority = 4)
    public void modifyingOdontologoWithId2() {
        OdontologoPage odontologoPage = new OdontologoPage(driver, null);

        Odontologo odontologo2ToModify = new Odontologo("OdontologoDos", "testDos", "ODO002");

        odontologoPage.navigateToModifyOdontologoForm();
        odontologoPage.insertNameToModify(odontologo2ToModify.getNombre());
        odontologoPage.insertLastNameToModify(odontologo2ToModify.getApellido());
        odontologoPage.insertMatriculaToModify(odontologo2ToModify.getMatricula());
        odontologoPage.clickBtnConfirmModifyOdontologo();


        String odontologoId = odontologoPage.getIdInTableFirstRow();
        String odontologoName = odontologoPage.getNameInTableFirstRow();
        String odontologoLastName = odontologoPage.getLastNameInTableFirstRow();
        String odontologoMatricula = odontologoPage.getMatriculaInTableFirstRow();

        Assert.assertEquals(parseInt(odontologoId), 2);
        Assert.assertEquals(odontologoName, odontologo2ToModify.getNombre());
        Assert.assertEquals(odontologoLastName, odontologo2ToModify.getApellido());
        Assert.assertEquals(odontologoMatricula, odontologo2ToModify.getMatricula());
    }

    @Test(priority = 5)
    public void addOdontologoWithId3() {
        OdontologoPage odontologoPage = new OdontologoPage(driver, null);

        odontologoPage.navigateToFormToAddOdontologo();
        odontologoPage.insertName(odontologo3.getNombre());
        odontologoPage.insertLastName(odontologo3.getApellido());
        odontologoPage.insertMatricula(odontologo3.getMatricula());
        odontologoPage.clickBtnFormAddOdontologo();

        String odontologoId = odontologoPage.getOdontologoAddedIntoTable(2, 1);
        String odontologoName = odontologoPage.getOdontologoAddedIntoTable(2, 2);
        String odontologoLastName = odontologoPage.getOdontologoAddedIntoTable(2, 3);
        String odontologoMatricula = odontologoPage.getOdontologoAddedIntoTable(2, 4);

        Assert.assertEquals(odontologoPage.tableRows().size(), 2);
        Assert.assertEquals(parseInt(odontologoId), 3);
        Assert.assertEquals(odontologoName, odontologo3.getNombre());
        Assert.assertEquals(odontologoLastName, odontologo3.getApellido());
        Assert.assertEquals(odontologoMatricula, odontologo3.getMatricula());
    }


    @AfterClass
    public void tearDown() {
        OdontologoPage odontologoPage = new OdontologoPage(driver, null);

        odontologoPage.close();
    }

}
