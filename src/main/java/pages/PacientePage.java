package pages;

import exceptions.MissingPropertyException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

public class PacientePage extends BasePage {

    public PacientePage(WebDriver driver, WebDriverWait wait) {
        super(driver, null);
    }

    private By pacienteCard = By.xpath("/html/body/section/a[2]/div");
    private By btnAddPacienteToOpenForm = By.xpath("/html/body/section[1]/article/button/a");

    private By pNoPacientesInTable = By.xpath("/html/body/section[1]/article/p");

    // LOCATORS - INPUTS FORM | ADD PACIENTE
    private By inputName = By.xpath("//*[@id=\"agregar-paciente\"]/form/input[1]");
    private By inputLastName = By.xpath("//*[@id=\"agregar-paciente\"]/form/input[2]");
    private By inputDNI = By.xpath("//*[@id=\"agregar-paciente\"]/form/input[3]");
    private By inputIncomeDate = By.xpath("//*[@id=\"agregar-paciente\"]/form/input[4]");
    private By inputStreet = By.xpath("//*[@id=\"agregar-paciente\"]/form/input[5]");
    private By inputNumber = By.xpath("//*[@id=\"agregar-paciente\"]/form/input[6]");
    private By inputLocation = By.xpath("//*[@id=\"agregar-paciente\"]/form/input[7]");

    private By btnAddPaciente = By.xpath("//*[@id=\"agregar-paciente\"]/form/button");

    // END LOCATORS - INPUTS FORM | ADD PACIENTE

    // LOCATORS - PACIENTE TABLE
    private By pacienteTable = By.xpath("//*[@id=\"pacientes-tabla\"]");

    private String pacienteTBodyTable = "//*[@id=\"pacientes-tabla\"]/tbody";

    private By firstPacienteIdInTable = By.xpath("/html/body/section[1]/article/table/tbody/tr/td[1]/button");

    private By firstPacienteNameInTable = By.xpath("/html/body/section[1]/article/table/tbody/tr/td[2]");

    private By firstPacienteLastNameInTable = By.xpath("/html/body/section[1]/article/table/tbody/tr/td[3]");

    private By firstPacienteBtnModify = By.xpath("/html/body/section[1]/article/table/tbody/tr/td[10]/button[1]");

    private By secondPacienteBtnDelete = By.xpath("/html/body/section[1]/article/table/tbody/tr[1]/td[10]/button[2]");

    // END LOCATORS - PACIENTE TABLE


    public void navigateToPaciente(String username, String password) throws MissingPropertyException, IOException {
        LoginPage loginPage = new LoginPage(driver, null);
        loginPage.navigateToLoginPage();
        loginPage.login(username, password);
        click(pacienteCard);
    }


    public void navigateToFormToAddPaciente() {
        click(btnAddPacienteToOpenForm);
    }

    public void insertName(String name) {
        sendKeys(inputName, name);
    }

    public void insertLastName(String lastName) {
        sendKeys(inputLastName, lastName);
    }

    public void insertDNI(String dni) {
        sendKeys(inputDNI, dni);
    }

    public void insertIncomeDate(String incomeDate) {
        sendKeys(inputIncomeDate, incomeDate);
    }

    public void insertStreet(String street) {
        sendKeys(inputStreet, street);
    }

    public void insertNumber(int number) {
        sendNumbers(inputNumber, number);
    }

    public void insertLocation(String location) {
        sendKeys(inputLocation, location);
    }

    public void addPaciente() {
        click(btnAddPaciente);
    }

    public String getPacienteAddedIntoTable(int row, int column)  {
        return getValueFromTable(pacienteTBodyTable, row, column);
    }

    public List<WebElement> tableRows() {
        return tableRows(pacienteTBodyTable);
    }

    public void clickBtnDeleteSecondPacienteInTable() {
        click(secondPacienteBtnDelete);
    }

    public void acceptAlertToDeletePaciente() {
        acceptAlert();
    }

    public String pacienteTableIsEmpty() {
        return getText(pNoPacientesInTable);
    }

}
