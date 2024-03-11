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

    private By firstPacienteBtnModify = By.xpath("/html/body/section[1]/article/table/tbody/tr/td[10]/button[1]");

    private By secondPacienteBtnDelete = By.xpath("/html/body/section[1]/article/table/tbody/tr[1]/td[10]/button[2]");

    // END LOCATORS - PACIENTE TABLE

    // LOCATOR - MODIFIY PACIENTE FORM
    private  By modifyInputName = By.xpath("/html/body/section[2]/form/input[2]");

    private  By modifyInputLastName = By.xpath("/html/body/section[2]/form/input[3]");

    private  By modifyInputDNI = By.xpath("/html/body/section[2]/form/input[4]");

    private  By modifyInputIncomeDate = By.xpath("/html/body/section[2]/form/input[5]");

    private  By modifyInputStreet = By.xpath("/html/body/section[2]/form/input[7]");

    private  By modifyInputNumber = By.xpath("/html/body/section[2]/form/input[8]");

    private  By modifyInputLocation = By.xpath("/html/body/section[2]/form/input[9]");

    private  By btnModifyPaciente = By.xpath("/html/body/section[2]/form/button");
    // END LOCATOR - MODIFIY PACIENTE FORM



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

    public String getFirstIdOnTable() {
        return getText(firstPacienteIdInTable);
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

    public void navigateToFormToModifiyPaciente() {
        click(firstPacienteBtnModify);
    }

    public void modifyInsertName(String name) {
        sendKeys(modifyInputName, name);
    }

    public void modifyInsertLastName(String lastName) {
        sendKeys(modifyInputLastName, lastName);
    }

    public void modifyInsertDNI(String dni) {
        sendKeys(modifyInputDNI, dni);
    }

    public void modifyInsertIncomeDate(String incomeDate) {
        sendKeys(modifyInputIncomeDate, incomeDate);
    }

    public void modifyInsertStreet(String street) {
        sendKeys(modifyInputStreet, street);
    }

    public void modifyInsertNumber(int number) {
        sendNumbers(modifyInputNumber, number);
    }

    public void modifyInsertLocation(String location) {
        sendKeys(modifyInputLocation, location);
    }

    public void modifiyPaciente() {
        click(btnModifyPaciente);
    }

}
