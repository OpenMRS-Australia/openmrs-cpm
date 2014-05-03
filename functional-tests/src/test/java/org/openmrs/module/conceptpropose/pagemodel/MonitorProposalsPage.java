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
        // wait for proposal page to load.
        // needed especially after clicking add/edit/save on create/edit proposal page as after clicking the button
        // it is still on the create/edit proposal page
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.getCurrentUrl().endsWith("/proposals.list#/");
            }
        });
        // the loading icon is the angular template before data is rendered
        final WebElement loadingIcon = driver.findElement(By.className("loading"));
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return !loadingIcon.isDisplayed();
            }
        });
    }

    public void navigateToDraftProposal(String name){
        findDraftProposalByName(name).click();
        final WebElement saveButton = getElementByAttribute("button", "ng-click", "save()");
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return saveButton.isEnabled();
            }
        });
    }
    public void navigateToDraftProposalWithConcepts(String name){
        findDraftProposalWithConcepts(name).click();
        final WebElement saveButton = getElementByAttribute("button", "ng-click", "save()");
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return saveButton.isEnabled();
            }
        });
    }
    public void navigateToDraftProposalWithNoConcepts(String name){
        findDraftProposalWithNoConcepts(name).click();
        final WebElement saveButton = getElementByAttribute("button", "ng-click", "save()");
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return saveButton.isEnabled();
            }
        });
    }
    public void navigateToProposal(String name){
        findDraftProposalByName(name).click();
        final WebElement saveButton = getElementByAttribute("button", "ng-click", "save()");
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return saveButton.isEnabled();
            }
        });
    }
    public WebElement findDraftProposalByName(String name){
        return findProposal("Draft", name, false, false);
    }
    public WebElement findDraftProposalWithConcepts(String name){
        return findProposal("Draft", name, true, false);
    }
    public WebElement findDraftProposalWithNoConcepts(String name){
        return findProposal("Draft", name, false, true);
    }
    public String findProposalStatus(String name){
        WebElement proposal = findProposal("", name, false, false);
        return proposal.findElements(By.className("ng-binding")).get(3).getText();
    }
    public int getProposalConceptCount(String name){
        WebElement proposal = findProposal("", name, false, false);
        try{
            return Integer.parseInt(proposal.findElements(By.className("ng-binding")).get(2).getText());
        }
        catch(Exception e){ return 0; }

    }
    public WebElement findProposal(String status, String name, boolean mustHaveConcepts, boolean mustHaveNoConcepts){
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
            boolean foundNoConcepts = false;
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
            if(mustHaveNoConcepts)
            {
                if(row.findElement(By.xpath(".//td[3]")).getText().equals("0"))
                    foundNoConcepts = true;
            }

            if(!status.equals("") && !foundStatus) continue;
            if(!name.equals("") && !foundName) continue;
            if(mustHaveConcepts && !foundConcepts) continue;
            if(mustHaveNoConcepts && !foundNoConcepts) continue;
            return row;
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
