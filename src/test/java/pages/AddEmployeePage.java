package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.BaseClass;

public class AddEmployeePage extends BaseClass {

    public By formLoaderLocator = By.cssSelector("div.oxd-form-loader");

    public By missingFieldErrorMsg = By.xpath("//span[text()='Required']");

    @FindBy(xpath = "//input[@name='firstName']")
    public WebElement firstNameField;

    @FindBy(xpath = "//input[@name='lastName']")
    public WebElement lastNameField;

    @FindBy(xpath = "//input[@name='middleName']")
    public WebElement middleNameField;

    @FindBy(xpath = "//label[normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')] //input")
    public WebElement idAutoGenField;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement submitBtn;


    public AddEmployeePage() {
        PageFactory.initElements(driver, this);
    }


    public String getErrorMsg(By locator) {
        return driver.findElement(locator).getText();
    }
}

