package pages;

import exceptions.MissingPropertyException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

public class OdontologoPage extends BasePage {

   // Locators - Odontologo Page
    private By odontologoCard = By.xpath("/html/body/section/a[1]");
    private By btnAddOdontologo = By.xpath("//*[@id=\"section-main\"]/article/button/a");
    private By inputSearchOdontologo  = By.xpath("//*[@id=\"form-search\"]/input");
    private By btnSearchOdontologo = By.xpath("//*[@id=\"btn-search--odontologos\"]");

    private String tableTBodyOdontologos = "//*[@id=\"odontologos-tabla\"]/tbody";
    private By btnDeleteOdontologo = By.xpath("/html/body/section[1]/article/table/tbody/tr/td[5]/button[2]");

    private By idTableFirstRow = By.xpath("/html/body/section[1]/article/table/tbody/tr/td[1]/button");
    private By nameTableFirstRow = By.xpath("/html/body/section[1]/article/table/tbody/tr/td[2]");
    private By lastNameTableFirstRow = By.xpath("/html/body/section[1]/article/table/tbody/tr[1]/td[3]");
    private By matriculaTableFirstRow = By.xpath("/html/body/section[1]/article/table/tbody/tr[1]/td[4]");


    // Locators - Form Add Odontologo
    private By inputNameOdontologo = By.xpath("//*[@id=\"agregar-odontologo\"]/form/input[1]");
    private By inputLastNameOdontologo = By.xpath("//*[@id=\"agregar-odontologo\"]/form/input[2]");
    private By inputMatriculaOdontologo = By.xpath("//*[@id=\"agregar-odontologo\"]/form/input[3]");
    private By btnFormAddOdontologo = By.xpath("//*[@id=\"agregar-odontologo\"]/form/button");
    private By odontologoTableIsEmpty = By.xpath("//*[@id=\"section-main\"]/article/p");

    // Locators - Form Modify odontologo
    private By btnModifyOdontologo = By.xpath("/html/body/section[1]/article/table/tbody/tr/td[5]/button[1]/a");
    private By inputNameOdontologoToModifying = By.xpath("//*[@id=\"modificar-odontologo\"]/form/input[2]");
    private By inputLastNameOdontologoToModifying = By.xpath("//*[@id=\"modificar-odontologo\"]/form/input[3]");
    private By inputMatriculaOdontologoToModifying = By.xpath("//*[@id=\"modificar-odontologo\"]/form/input[4]");
    private By btnConfirmModifyOdontologo = By.xpath("/html/body/section[2]/form/button");


    public OdontologoPage(WebDriver driver, WebDriverWait wait) {
        super(driver, null);
    }


    public void navigateToOdontologo(String username, String password) throws MissingPropertyException, IOException {
        LoginPage loginPage = new LoginPage(driver, null);
        loginPage.navigateToLoginPage();
        loginPage.login(username, password);
        click(odontologoCard);
    }


    public void navigateToFormToAddOdontologo() {
        click(btnAddOdontologo);
    }


    public void insertName(String name) {
        sendKeys(inputNameOdontologo, name);
    }


    public void insertLastName(String lastName) {
        sendKeys(inputLastNameOdontologo, lastName);
    }


    public void insertMatricula(String matricula) {
        sendKeys(inputMatriculaOdontologo, matricula);
    }


    public void clickBtnFormAddOdontologo() {
        click(btnFormAddOdontologo);
    }

    public String getOdontologoAddedIntoTable(int row, int column)  {
        return getValueFromTable(tableTBodyOdontologos, row, column);
    }

    public List<WebElement> tableRows() {
        return tableRows(tableTBodyOdontologos);
    }

    public void clickBtnDeleteFirstOdontologoInTable() {
        click(btnDeleteOdontologo);
    }

    public void acceptAlertToDeleteOdontologo() {
        acceptAlert();
    }

    public String odontologoTableIsEmpty() {
        return getText(odontologoTableIsEmpty);
    }
    public void navigateToModifyOdontologoForm() {
        click(btnModifyOdontologo);
    }

    public void insertNameToModify(String name) {
        sendKeys(inputNameOdontologoToModifying, name);
    }


    public void insertLastNameToModify(String lastName) {
        sendKeys(inputLastNameOdontologoToModifying, lastName);
    }

    public void insertMatriculaToModify(String matricula) {
        sendKeys(inputMatriculaOdontologoToModifying, matricula);
    }

    public void clickBtnConfirmModifyOdontologo() {
        click(btnConfirmModifyOdontologo);
    }

    public String getIdInTableFirstRow() {
        return getText(idTableFirstRow);
    }

    public String getNameInTableFirstRow() {
        return getText(nameTableFirstRow);
    }

    public String getLastNameInTableFirstRow() {
        return getText(lastNameTableFirstRow);
    }

    public String getMatriculaInTableFirstRow() {
        return getText(matriculaTableFirstRow);
    }

}
