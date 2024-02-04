package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import pages.LoginPage;


public class LoginTest {
    public WebDriver driver;

    @BeforeEach
    public void preconditions() {
        driver = new ChromeDriver();

        LoginPage loginPage = new LoginPage(driver, null);
        loginPage.setup();
        loginPage.navigateToLoginPage();
    }


    @Test
    public void LoginSuccess() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver, null);


        String username = "admin@clinica.com";
        String password = "admin";


        loginPage.insertUsername(username);
        loginPage.insertPassword(password);
        loginPage.clickSignInBtn();


        Assert.assertTrue(loginPage.containerOdontologoIsDisplayed());
    }


    @Test
    public void LoginFailed() {
        LoginPage loginPage = new LoginPage(driver, null);

        String username = "admin@clinica.com";
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
