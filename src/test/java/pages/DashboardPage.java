package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.BaseClass;

public class DashboardPage extends BaseClass {

    @FindBy(xpath = "//h6[text()='Dashboard']")
    public WebElement dashTopBarHeader;

    @FindBy(xpath = "//span[text()='PIM']")
    public WebElement pim;

    @FindBy(xpath = "//a[text()='Add Employee']")
    public WebElement addEmpTab;

    @FindBy(xpath = "//a[text()='Employee List']")
    public WebElement empListTab;

    public DashboardPage() {
        PageFactory.initElements(driver, this);
    }

}
