package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.BaseClass;

public class LoginPage extends BaseClass {

    public By requiredError = By.xpath("//*[text()='Required']");

    public By invalidCredentialsError = By.xpath("//*[text()='Invalid credentials']");

    @FindBy(xpath = "//*[@name='username']")
    public WebElement userInput;

    @FindBy(xpath = "//*[@name='password']")
    public WebElement passInput;

    @FindBy(xpath = "//*[text()=' Login ']")
    public WebElement loginBtn;


    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    public String getErrorMsg(By locator) {
        return driver.findElement(locator).getText();
    }
}
