package org.openmrs.module.conceptpropose.pagemodel;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class CreateProposalPage extends BaseCpmPage {
    private By nameSelector = By.name("name");
    private By emailSelector = By.name("email");
    private By commentSelector = By.tagName("textarea");
    private By buttonSelector = By.tagName("button");
    public CreateProposalPage(WebDriver driver) {
        super(driver);
    }
    public String getProposalID(){
        String url = driver.getCurrentUrl();
        return url.substring(url.lastIndexOf("/") +1 );
    }
    public int getNumberOfConcepts(){
        final WebElement loadingIcon = driver.findElement(By.cssSelector("#cpmapp .loading"));
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return !loadingIcon.isDisplayed();
            }
        });
        final WebElement noConcepts = driver.findElement(By.cssSelector(".conceptTable .noConceptsMsg"));
        if(noConcepts.isDisplayed()) return 0;
        // List <WebElement> resultRowsElement = driver.findElements(By.xpath("//tbody[@class='conceptList']/tr"));
        List <WebElement> resultRowsElement = driver.findElements(By.cssSelector(".conceptTable .conceptList tr"));
        return resultRowsElement.size();
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

        final WebElement emailElement = driver.findElement(emailSelector);
        emailElement.clear();
        emailElement.sendKeys(email);

        final WebElement commentElement = driver.findElement(commentSelector );
        commentElement.clear();
        commentElement.sendKeys(someComments);
    }

    public String getName(){
        return driver.findElement(nameSelector).getAttribute("value");
    }
    public String getEmail(){
        return driver.findElement(emailSelector).getAttribute("value");
    }
    public String getComment(){
        return driver.findElement(commentSelector).getAttribute("value");
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
                .findElements(By.tagName("input"))
                .get(0);
        commentBox.clear();
        commentBox.sendKeys(comment);
    }

    public String getConceptComment() {
        WebElement commentBox = driver
                .findElement(By.className("conceptTable"))
                .findElements(By.tagName("input"))
                .get(0);
        return commentBox.getAttribute("value");
    }

    public MonitorProposalsPage editExistingProposal() {
        getElementByAttribute("button", "ng-click", "save()").click();
        return new MonitorProposalsPage(driver);
    }

    public void deleteExistingConcept() {
        getElementByAttribute("button", "ng-click", "removeConcept(concept)").click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
    public MonitorProposalsPage saveNewProposal() {
        final WebElement saveProposalButton = getElementByAttribute("button", "ng-click", "save()");
        saveProposalButton.click();
        return new MonitorProposalsPage(driver);
    }
    public void deleteProposal() {
        getElementByAttribute("button", "ng-click", "deleteProposal()").click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
    public void navigateToAddConceptDialog(){
        WebElement addConceptButton = getElementByAttribute("button", "ng-click", "openSearchConceptDialog()");
        addConceptButton.click();
    }
}
