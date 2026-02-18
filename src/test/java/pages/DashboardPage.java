package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.BaseClass;

public class DashboardPage extends BaseClass {

        @FindBy(xpath = "//h6[text()='Dashboard']")
    public WebElement dashTopBarHeader;

    public DashboardPage() {
        PageFactory.initElements(driver, this);
    }

}
