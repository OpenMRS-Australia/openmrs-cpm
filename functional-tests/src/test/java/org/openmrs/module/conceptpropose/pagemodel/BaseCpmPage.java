package org.openmrs.module.conceptpropose.pagemodel;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseCpmPage {
    public static final int DEFAULT_TIMEOUT_IN_SECONDS = 30;
    protected final WebDriver driver;
    protected WebDriverWait defaultWait;

    public BaseCpmPage(WebDriver driver) {
        this.driver = driver;
        this.driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
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
    public WebElement getElementByAttribute(String tagName, String attribute, String valueToMatch) {
        List<WebElement> elements = elements = driver.findElements(By.tagName(tagName));
        return getElementByAttributeFromElementList(elements, attribute, valueToMatch);
    }
    public WebElement getElementByAttribute(WebElement parent, String tagName, String attribute, String valueToMatch) {
        List<WebElement> elements = parent.findElements(By.tagName(tagName));
        return getElementByAttributeFromElementList(elements, attribute, valueToMatch);
    }
    public WebElement getElementByAttributeFromElementList(List<WebElement> elements, String attribute, String valueToMatch){
        for (WebElement e : elements){
            if (e.getAttribute(attribute).equals(valueToMatch)) {
                return e;
            }
        }
        return null;
    }


}
