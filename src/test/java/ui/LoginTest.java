package ui;

import config.ConfigProperties;
import exceptions.MissingPropertyException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import pages.LoginPage;

import java.io.IOException;


public class LoginTest {
    public WebDriver driver;

    private ConfigProperties configProperties = new ConfigProperties();

    private String getConfigProperties(String property, String propFileName) throws IOException, MissingPropertyException {
        configProperties.loadProperties(propFileName);

        return configProperties.getProp(property);
    }

    @BeforeEach
    public void preconditions() throws MissingPropertyException, IOException {
        driver = new ChromeDriver();

        LoginPage loginPage = new LoginPage(driver, null);
        loginPage.setup();
        loginPage.navigateToLoginPage();
    }


    @Test
    public void LoginSuccess() throws InterruptedException, MissingPropertyException, IOException {
        LoginPage loginPage = new LoginPage(driver, null);


        String username = getConfigProperties("USER_ADMIN", "config");
        String password = getConfigProperties("PASSWORD_ADMIN", "config");


        loginPage.insertUsername(username);
        loginPage.insertPassword(password);
        loginPage.clickSignInBtn();


        Assert.assertTrue(loginPage.containerOdontologoIsDisplayed());
    }


    @Test
    public void LoginFailedPassowrdIncorrect() throws MissingPropertyException, IOException {
        LoginPage loginPage = new LoginPage(driver, null);

        String username = getConfigProperties("USER_ADMIN", "config");
        String password = "adminn";

        loginPage.insertUsername(username);
        loginPage.insertPassword(password);
        loginPage.clickSignInBtn();


        Assert.assertTrue(loginPage.messageBadCredentials());
    }


    @AfterEach
    public void tearDown() {
        LoginPage loginPage = new LoginPage(driver, null);

        loginPage.close();
    }
}
