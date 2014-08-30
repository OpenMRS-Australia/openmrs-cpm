package org.openmrs.module.conceptreview.functionaltest.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.firefox.FirefoxDriver;

// TODO - refactor Login , SeleniumDriver and QueryBrowserPage to common package? tried to do so but IntelliJ wasn't able to run the review functional tests correctly
public class SeleniumDriver {

    private static FirefoxDriver driver = null;

    @Before("@Selenium")
    public void startScenario() {
        driver = new FirefoxDriver();
    }

    public static synchronized FirefoxDriver getDriver() {
        return driver;
    }

    @After("@Selenium")
    public static void endScenario() {
        driver.quit();
    }

}
