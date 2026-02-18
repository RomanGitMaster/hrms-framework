package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.BaseClass;

public class LoginPage extends BaseClass {

    @FindBy(xpath = "//*[@name='username']")
    public WebElement userInput;

    @FindBy(xpath = "//*[@name='password']")
    public WebElement passInput;

    @FindBy(xpath = "//*[text()=' Login ']")
    public WebElement loginBtn;

    @FindBy(xpath = "//*[text()='Required']")
    public WebElement emptyFieldError;

    @FindBy(xpath = "//*[text()='Invalid credentials']")
    public WebElement invalidCredentialsError;


    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

   public String getErrorMsg(WebElement element){
       return element.getText();
   }
}
