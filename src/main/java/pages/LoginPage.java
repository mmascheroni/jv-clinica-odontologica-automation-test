package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {

    // Locators
    private By usernameInput = By.id("username");

    private By passwordInput = By.id("password");

    private By buttonSignIn = By.xpath("/html/body/div/form/button");

    private By containerOdolontogoAfterLogin = By.xpath("/html/body/section/a[1]");

    private By messageBadCredentials = By.xpath("/html/body/div/form/div");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, null);
    }

    public void navigateToLoginPage() {
        url("http://localhost:8082");
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
}
