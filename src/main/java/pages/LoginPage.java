package pages;

import config.ConfigProperties;
import exceptions.MissingPropertyException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class LoginPage extends BasePage {

    // Locators
    private By usernameInput = By.id("username");

    private By passwordInput = By.id("password");

    private By buttonSignIn = By.xpath("/html/body/div/form/button");

    private By containerOdolontogoAfterLogin = By.xpath("/html/body/section/a[1]");

    private By messageBadCredentials = By.xpath("/html/body/div/form/div");

    private ConfigProperties configProperties = new ConfigProperties();


    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, null);
    }

    public String getConfigProperties(String property) throws IOException, MissingPropertyException {
        configProperties.loadProperties("config");

        return configProperties.getProp(property);
    }


    public void navigateToLoginPage() throws MissingPropertyException, IOException {
        String url = getConfigProperties("BASE_URL");
        url(url);
    }

    public void insertUsername(String username) {
        sendKeys(usernameInput, username);
    }


    public void insertPassword(String password) {
        sendKeys(passwordInput, password);
    }


    public void clickSignInBtn() {
        click(buttonSignIn);
    }


    public Boolean containerOdontologoIsDisplayed() {
        return findElement(containerOdolontogoAfterLogin).isDisplayed();
    }


    public Boolean messageBadCredentials() {
        return findElement(messageBadCredentials).isDisplayed();
    }

    public void login(String username, String password) {
        sendKeys(usernameInput, username);
        sendKeys(passwordInput, password);
        click(buttonSignIn);
    }
}
