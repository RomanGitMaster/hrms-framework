package utils;

import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


public class BaseClass extends PageInitializer {
    protected static Scenario scenario;
    public static WebDriver driver;


    public void openBrowser() {
        String browser = ConfigReader.read("browser").toLowerCase();
        String url = ConfigReader.read("url").toLowerCase();

        switch (browser) {

            case "chrome":
                driver = new ChromeDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;

            default:
                throw new IllegalArgumentException("Invalid browser name: " + browser + " . Allowed: chrome, firefox, edge");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));// comment out if global wait is not really required in test case
        driver.get(url);
        initializePageObjects();
    }

    public void closeBrowser() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("Could not quit the driver. Reason: " + e.getMessage());
            } finally {
                driver = null;
            }
        }
    }


    //EXPLICIT WAIT HELPERS
    public WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
    }

    public WebElement waitClickable(WebElement element) {
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitClickable(By locator) {
        return getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitInvisible(By locator) {
        return getWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public WebElement waitVisible(WebElement element) {
        return getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitVisible(By locator) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForFormLoader(By locator) {
        try {
            getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
            // getWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            // If it never appeared (or appeared too quickly), we just continue
        }
    }


    //SEND TEXT HELPERS
    public void sendText(WebElement element, String text) {
        WebElement el = waitVisible(element);
        el.clear();
        el.sendKeys(text);
    }

    public void sendText(By locator, String text) { //custom helper of sending text  with few retries
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement el = waitVisible(locator);
                el.clear();
                el.sendKeys(text);
                return;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
        throw new RuntimeException("Stale element after " + attempts + " retries (sendText) " + locator);
    }

    public void clearAndType(WebElement el, String text) {
        waitClickable(el).click();
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        el.sendKeys(Keys.DELETE);

        el.sendKeys(text);
    }

    public void typeIfNotEmpty(WebElement element, String value) {
        if (value != null && !value.isEmpty()) {
            sendText(element, value);
        }
    }

    public void clearField(WebElement el) {
        waitClickable(el).click();
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        el.sendKeys(Keys.DELETE);
    }


    //CUSTOM CLICK
    public void click(WebElement el) {
        waitClickable(el).click();
    }

    public void click(By locator) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                waitClickable(locator).click();
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                attempts++;
            }
        }
        throw new RuntimeException("Stale element after " + attempts + " retries (sendText) " + locator);
    }


    //SELECT DROPDOWN
    public void selectFromDropDown(WebElement dropDown, String visibleText) {
        Select sel = new Select(dropDown);
        sel.selectByVisibleText(visibleText);
    }

    public void selectFromDropDown(String value, WebElement dropDown) {
        Select sel = new Select(dropDown);
        sel.selectByValue(value);
    }

    public void selectFromDropDown(WebElement dropDown, int index) {
        Select sel = new Select(dropDown);
        sel.selectByIndex(index);
    }


    //JAVASCRIPT EXECUTOR
    public JavascriptExecutor getJSExecutor() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js;
    }

    public void jsClick(WebElement element) {
        getJSExecutor().executeScript("arguments[0].click();", element);
    }


    //SCREENSHOT
    public byte[] takeScreenshot(String fileName) {
        //it accepts array of byte in cucumber for the screenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        byte[] picByte = ts.getScreenshotAs(OutputType.BYTES);
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(sourceFile,
                    new File(Constants.SCREENSHOT_FILEPATH +
                            fileName + " " +
                            getTimeStamp("yyyy-MM-dd-HH-mm-ss") + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picByte;
    }

    public void scenarioScreenshot() {
        String safeName = scenario.getName().replaceAll("[^a-zA-Z0-9-_]", "_");
        byte[] pic = takeScreenshot(safeName);
        scenario.attach(pic, "image/png", safeName);
    }


    //TIMESTAMP
    public String getTimeStamp(String pattern) {
        //this method will return the timestamp which we will add in ss method
        Date date = new Date();

        //yyyy-mm-dd-hh-mm-ss
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }


}
