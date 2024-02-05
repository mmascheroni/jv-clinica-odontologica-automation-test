package ui;

import config.ConfigProperties;
import exceptions.MissingPropertyException;
import models.Odontologo;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import pages.OdontologoPage;

import java.io.IOException;

import static java.lang.Integer.parseInt;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OdontologoTest {

    public WebDriver driver;

    private ConfigProperties configProperties = new ConfigProperties();

    private Odontologo odontologo1 = new Odontologo("OdontologoUno", "testUno", "ODO001");

    private String getConfigProperty(String property, String propFileName) throws IOException, MissingPropertyException {
        configProperties.loadProperties(propFileName);

        return configProperties.getProp(property);
    }

    @BeforeAll
    public void preconditions() throws MissingPropertyException, IOException {
        driver = new ChromeDriver();

        OdontologoPage odontologoPage = new OdontologoPage(driver, null);
        odontologoPage.setup();



        odontologoPage.navigateToOdontologo(getConfigProperty("USER_ADMIN", "config"), getConfigProperty("PASSWORD_ADMIN", "config"));
    }

    @Test
    public void AddOdontologo() {
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


    @AfterAll
    public void tearDown() {
        OdontologoPage odontologoPage = new OdontologoPage(driver, null);

        odontologoPage.close();
    }

}
