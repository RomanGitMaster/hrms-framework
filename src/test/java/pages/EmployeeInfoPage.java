package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.BaseClass;

import java.util.List;
import java.util.Map;

public class EmployeeInfoPage extends BaseClass {
    By rowLocator = By.xpath("//div[@class='oxd-table-card']");

    By cbLocator = By.xpath("//div[@class='oxd-table-card']/div//div[contains(@class,'oxd-table-card-cell-checkbox')]");

    By recordFound = By.xpath("//span[contains(.,'Record Found')]");

    @FindBy(xpath = "//form[@class='oxd-form']//input[contains(@class,'oxd-input')]")
    public WebElement empIdSearchfield;

    @FindBy(xpath = "//button[text()=' Search ']")
    public WebElement searchBtn;

    @FindBy(xpath = "//button[text()=' Delete Selected ']")
    public WebElement deleteBtn;

    @FindBy(xpath = "//button[text()=' Yes, Delete ']")
    public WebElement confDeleteBtn;


    public EmployeeInfoPage() {
        PageFactory.initElements(driver, this);
    }


    //to verify added employee
    public void verifyEmp(Map<String, String> map, String expEmp, String actEmp) {
        String expectedId = map.get("empId");
        expEmp = map.get("empId") + " " + map.get("firstName") + " " + map.get("middleName") + " " + map.get("lastName");
        System.out.println("Expected employee: " + expEmp);

        getWait().until(ExpectedConditions.textToBePresentInElementLocated(recordFound, "(1) Record Found"));
        WebElement empRow = driver.findElement(rowLocator);

        WebElement checkBox = driver.findElement(cbLocator);
        if (empRow.getText().trim().contains(expectedId)) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", checkBox);
            click(cbLocator);
            List<WebElement> cells = empRow.findElements(By.xpath(".//div[contains(@class,'oxd-table-cell')]"));
            StringBuilder sb = new StringBuilder();
            for (WebElement cell : cells) {
                String text = cell.getText();
                if (!cell.getText().isEmpty()) {
                    sb.append(text + " ");
                }
            }
            actEmp = sb.toString().trim();
            System.out.println("Found  employee: " + actEmp);
        } else {
            System.out.println("No such employee: " + expEmp);
        }
    }


    //to delete employee after it was verified
    public void deleteVerifiedEmp() {
        click(deleteBtn);
        click(confDeleteBtn);
    }

}
