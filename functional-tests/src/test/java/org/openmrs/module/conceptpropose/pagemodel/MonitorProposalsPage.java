package org.openmrs.module.conceptpropose.pagemodel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class MonitorProposalsPage extends BaseCpmPage {
    private By resultsSelector = By.className("results");
    public MonitorProposalsPage(WebDriver driver) {
        super(driver);
    }

    public String getLastProposalName(){
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.findElements(resultsSelector).size() > 0;
            }
        });

        List <WebElement> resultRowsElement = driver.findElements(By.className("ng-scope"));
        WebElement lastRowOfResults =  resultRowsElement.get(resultRowsElement.size()-2);
        return lastRowOfResults.findElements(By.className("ng-binding")).get(0).getText();
    }
    public String getLastProposalDescription(){
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.findElements(resultsSelector).size() > 0;
            }
        });

        List <WebElement> resultRowsElement = driver.findElements(By.className("ng-scope"));
        WebElement lastRowOfResults =  resultRowsElement.get(resultRowsElement.size()-2);
        return lastRowOfResults.findElements(By.className("ng-binding")).get(1).getText();
    }
}
