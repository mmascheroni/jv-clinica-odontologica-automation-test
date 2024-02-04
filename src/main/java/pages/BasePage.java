package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    public WebDriver driver;

    public WebDriverWait wait;

    protected BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofMillis(3000));
    }

    public void setup() {
        WebDriverManager.chromedriver().setup();

        driver.manage().window().maximize();
    }

    protected void url(String url) {
        driver.get(url);
    }

    public void close() {
        driver.quit();
    }

    protected void refreshPage() {
        driver.navigate().refresh();
    }

    protected WebElement findElement(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElement(locator);
    }

    protected WebElement findElementByXPath(String locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    protected void sendKeys(By locator, String keys) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        findElement(locator).clear();
        findElement(locator).sendKeys(keys);
    }

    protected void sendKey(CharSequence key, By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        this.findElement(locator).sendKeys(key);
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        findElement(locator).click();
    }

    protected String getText(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return findElement(locator).getText();
    }


    protected String getValueFromTable(String locator, int row, int column) {
        String cellINeed = locator+"/tr["+row+"]/td["+column+"]";

        return findElementByXPath(cellINeed).getText();
    }


    protected List<WebElement> tableRows(String locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator+"/tr")));
        return findElementByXPath(locator).findElements(By.tagName("tr"));
    }
}
