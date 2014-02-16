package org.openmrs.module.cpm.pagemodel;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CreateProposalPage extends BaseCpmPage {
    private By nameSelector = By.name("name");
    public CreateProposalPage(WebDriver driver) {
        super(driver);
    }

    public void enterNewProposal(String someName, String email, String someComments) {
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.findElements(nameSelector).size() > 0;
            }
        });
        final WebElement nameElement = driver.findElement(nameSelector);
        nameElement.clear();
        nameElement.sendKeys(someName);

        final WebElement emailElement = driver.findElement(By.name("email"));
        emailElement.clear();
        emailElement.sendKeys(email);

        final WebElement commentElement = driver.findElement(By.tagName("textarea"));
        commentElement.clear();
        commentElement.sendKeys(someComments);
    }

    public void saveNewProposal() {
        driver.findElements(By.tagName("button")).get(1).click();

        defaultWait.until(ExpectedConditions.alertIsPresent());
        // Before you try to switch to the so given alert, he needs to be present.

        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
}
