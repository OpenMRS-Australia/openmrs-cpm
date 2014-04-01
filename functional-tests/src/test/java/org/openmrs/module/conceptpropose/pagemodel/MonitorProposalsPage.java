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

    public void waitUntilFullyLoaded(){
        final WebElement loadingIcon = driver.findElement(By.className("loading"));
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return !loadingIcon.isDisplayed();
            }
        });
    }
    public void navigateToDraftProposal(String name){
        findDraftProposal(name, false).click();
        final WebElement saveButton = getElementByAttribute("button", "ng-click", "save()");
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return saveButton.isEnabled();
            }
        });
    }
    public void navigateToDraftProposalWithConcepts(String name){
        findDraftProposal(name, true).click();
        final WebElement saveButton = getElementByAttribute("button", "ng-click", "save()");
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return saveButton.isEnabled();
            }
        });
    }
    public void navigateToProposal(String name){
        findDraftProposal(name, false).click();
        final WebElement saveButton = getElementByAttribute("button", "ng-click", "save()");
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return saveButton.isEnabled();
            }
        });
    }
    public WebElement findDraftProposal(String name, boolean mustHaveConcepts){
        return findProposal("Draft", name, mustHaveConcepts);
    }
    public String findProposalStatus(String name){
        WebElement proposal = findProposal("", name, false);
        return proposal.findElements(By.className("ng-binding")).get(3).getText();
    }
    public int getProposalConceptCount(String name){
        WebElement proposal = findProposal("", name, false);
        try{
            return Integer.parseInt(proposal.findElements(By.className("ng-binding")).get(2).getText());
        }
        catch(Exception e){ return 0; }

    }
    public WebElement findProposal(String status, String name, boolean mustHaveConcepts){
        if(name == null) name = "";
        else name = name.trim();
        if(status == null) status = "";
        else status = status.trim();

        List<WebElement> resultRowsElement = driver.findElements(By.xpath("//tbody[@class='results']/tr"));
        for (WebElement row : resultRowsElement)
        {
            boolean foundStatus = false;
            boolean foundName = false;
            boolean foundConcepts = false;
            if (row.findElement(By.xpath(".//td[4]")).getText().contains(status))
            {
                foundStatus = true;
            }
            if(!name.equals(""))
            {
                if(row.findElement(By.xpath(".//td[1]")).getText().contains(name))
                    foundName = true;
            }
            if(mustHaveConcepts)
            {
                if(!row.findElement(By.xpath(".//td[3]")).getText().equals("0"))
                    foundConcepts = true;
            }

            if(!status.equals("") && foundStatus && name.equals("") && !mustHaveConcepts) return row;
            if(!status.equals("") && foundStatus && name.equals("") && mustHaveConcepts && foundConcepts) return row;
            if(!status.equals("") && foundStatus && !name.equals("") && foundName && !mustHaveConcepts) return row;
            if(!status.equals("") && foundStatus && !name.equals("") && foundName && mustHaveConcepts && foundConcepts) return row;
            if(status.equals("") && name.equals("") && !mustHaveConcepts) return row;
            if(status.equals("") && name.equals("") && mustHaveConcepts && foundConcepts) return row;
            if(status.equals("") && !name.equals("") && foundName && !mustHaveConcepts) return row;
            if(status.equals("")  && !name.equals("") && foundName && mustHaveConcepts && foundConcepts) return row;
        }
        return null;
    }
    public void goToEditPageOfLastItem(){
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.findElements(resultsSelector).size() > 0;
            }
        });

        List <WebElement> resultRowsElement = driver.findElements(By.className("ng-scope"));
        WebElement lastRowOfResults =  resultRowsElement.get(resultRowsElement.size()-1);
        lastRowOfResults.findElements(By.className("ng-binding")).get(0).click();
    }

    public String getLastProposalName(){
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.findElements(resultsSelector).size() > 0;
            }
        });

        List <WebElement> resultRowsElement = driver.findElements(By.className("ng-scope"));
        WebElement lastRowOfResults =  resultRowsElement.get(resultRowsElement.size()-1);
        return lastRowOfResults.findElements(By.className("ng-binding")).get(0).getText();
    }
    public String getLastProposalDescription(){
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.findElements(resultsSelector).size() > 0;
            }
        });

        List <WebElement> resultRowsElement = driver.findElements(By.className("ng-scope"));
        WebElement lastRowOfResults =  resultRowsElement.get(resultRowsElement.size()-1);
        return lastRowOfResults.findElements(By.className("ng-binding")).get(1).getText();
    }
    public String getConceptCount(){
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.findElements(resultsSelector).size() > 0;
            }
        });

        List <WebElement> resultRowsElement = driver.findElements(By.className("ng-scope"));
        WebElement lastRowOfResults =  resultRowsElement.get(resultRowsElement.size()-1);
        return lastRowOfResults.findElements(By.className("ng-binding")).get(2).getText();
    }
    public String getLastProposalStatus(){
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.findElements(resultsSelector).size() > 0;
            }
        });

        List <WebElement> resultRowsElement = driver.findElements(By.className("ng-scope"));
        WebElement lastRowOfResults =  resultRowsElement.get(resultRowsElement.size()-1);
        return lastRowOfResults.findElements(By.className("ng-binding")).get(3).getText();
    }

}
