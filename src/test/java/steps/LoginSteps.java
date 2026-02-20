package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utils.BaseClass;
import utils.ConfigReader;


public class LoginSteps extends BaseClass {


    @Given("user is on the login page of HRMS")
    public void user_is_on_the_login_page_of_hrms() {
        String expectedTitle = "SyntaxHRM";
        Assert.assertEquals(expectedTitle, driver.getTitle());
    }


    @When("user enters username {string} and password {string}")
    public void user_enters_username_and_password(String username, String password) {
        typeIfNotEmpty(loginPage.userInput, username);
        typeIfNotEmpty(loginPage.passInput, password);
        click(loginPage.loginBtn);
    }

    @Then("user is prompted with error {string}")
    public void user_is_prompted_with_error(String expected) {
        Assert.assertEquals(expected, loginPage.getErrorMsg(loginPage.requiredError));
    }

    @Then("user is prompted with {string} message")
    public void user_is_prompted_with_message(String expected) {
        Assert.assertEquals(expected, loginPage.getErrorMsg(loginPage.invalidCredentialsError));
    }


    @When("user enters incorrect  {string} or {string}")
    public void user_enters_incorrect_or(String username, String password) {
        sendText(loginPage.userInput, username);
        sendText(loginPage.passInput, password);
        click(loginPage.loginBtn);
    }

    @When("user enters valid username and password")
    public void user_enters_valid_username_and_password() {
        sendText(loginPage.userInput, ConfigReader.read("username"));
        sendText(loginPage.passInput, ConfigReader.read("password"));
        click(loginPage.loginBtn);
    }

    @Then("user should be logged in successfully")
    public void user_should_be_logged_in_successfully() {
        Assert.assertTrue(dashPage.dashTopBarHeader.isDisplayed());
    }


    @Given("user is logged in with valid credentials")
    public void user_is_logged_in_with_valid_credentials() {
        sendText(loginPage.userInput, ConfigReader.read("username"));
        sendText(loginPage.passInput, ConfigReader.read("password"));
        click(loginPage.loginBtn);
        Assert.assertTrue(dashPage.dashTopBarHeader.isDisplayed());
    }

}
