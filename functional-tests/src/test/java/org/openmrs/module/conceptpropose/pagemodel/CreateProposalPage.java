package org.openmrs.module.conceptpropose.pagemodel;

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CreateProposalPage extends BaseCpmPage {
    private By nameSelector = By.name("name");
    private By buttonSelector = By.tagName("button");
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
    public void submitProposal() {
        getElementByAttribute("button", "ng-click", "submit()").click();
    }


    public void enterNewConcept(String conceptToSearch, int numberToAdd) {
        final WebElement addConceptContainer = driver.findElement(By.className("resultsContainer"));
        final WebElement searchBox = driver.findElement(By.className("searchBox"));
        searchBox.sendKeys(conceptToSearch);

        WebElement conceptListTable = addConceptContainer.findElement(By.className("conceptList"));
        List<WebElement> resultRowsElement = conceptListTable.findElements(By.tagName("tr"));
        int changed = 0;
        for(int i = 0; i < resultRowsElement.size(); i++){
            WebElement row =  resultRowsElement.get(i);
            row.findElements(By.tagName("input")).get(0).click();
            changed++;
            if(changed >= numberToAdd){
                break;
            }
        }
        WebElement footer = driver.findElement(By.className("dialogFooter"));
        WebElement addConceptButton = getElementByAttribute(footer, "button", "ng-click", "add()");
        addConceptButton.click();
    }
    public void enterNewConceptComment(String comment) {
        WebElement commentBox = driver
                .findElement(By.className("conceptTable"))
                .findElement(By.tagName("input"));
        commentBox.clear();
        commentBox.sendKeys(comment);
    }


    public void editExistingProposal() {
        getElementByAttribute("button", "ng-click", "save()").click();
    }

    public void saveNewProposal() {
        final WebElement saveProposalButton = getElementByAttribute("button", "ng-click", "save()");
        saveProposalButton.click();
    }
    public void navigateToAddConceptDialog(){
        WebElement addConceptButton = getElementByAttribute("button", "ng-click", "openSearchConceptDialog()");
        addConceptButton.click();
    }
}
