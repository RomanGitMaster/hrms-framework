package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import utils.BaseClass;

public class Hooks extends BaseClass {

    //pre condition
    @Before
    public void start(Scenario scenario) {
        BaseClass.scenario = scenario;
        System.out.println("Starting Test: " + scenario.getName());
        openBrowser();
    }


    //after condition
    @After
    public void end(Scenario scenario) {
        System.out.println("Ending Test: " + scenario.getName());
        System.out.println(scenario.getStatus());

        //taking screenshot on passed or failed testcase
        byte[] pic;
        if (scenario.isFailed()) {
            pic = takeScreenshot("failed/" + scenario.getName());
        } else {
            pic = takeScreenshot("passed/" + scenario.getName());

        }
        scenario.attach(pic, "image/png", scenario.getName());
          closeBrowser();
    }

}
