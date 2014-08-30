package org.openmrs.module.conceptpropose.functionaltest.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.firefox.FirefoxDriver;

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
