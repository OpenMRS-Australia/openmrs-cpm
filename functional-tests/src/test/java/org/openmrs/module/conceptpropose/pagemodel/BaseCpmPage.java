package org.openmrs.module.conceptpropose.pagemodel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseCpmPage {
    public static final int DEFAULT_TIMEOUT_IN_SECONDS = 30;
    protected final WebDriver driver;
    protected WebDriverWait defaultWait;

    public BaseCpmPage(WebDriver driver) {
        this.driver = driver;
        defaultWait = new WebDriverWait(driver, DEFAULT_TIMEOUT_IN_SECONDS);
    }

    public String getHeaderText() {
        final By headerText = By.id("headerText");

        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.findElements(headerText).size() > 0;
            }
        });

        return driver.findElement(headerText).getText();
    }
}
