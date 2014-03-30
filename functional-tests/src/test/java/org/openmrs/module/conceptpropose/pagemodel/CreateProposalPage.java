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

        addConceptToProposal();
    }
    public void submitProposal() {
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.findElements(buttonSelector).get(2).isEnabled();
            }
        });
        driver.findElements(buttonSelector).get(2).click();
    }

    public void addConceptToProposal() {
        // needs a better way
        driver.findElements(By.tagName("button")).get(0).click();
        addNewConceptsToProposalFromAddConceptPage();
    }

    public void addNewConceptsToProposalFromAddConceptPage() {
        // .resultsContainer table.searchConceptResults tbody.conceptList.results > tr > td > input
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.findElements(By.className("searchBox")).size() > 0;
            }
        });

        final WebElement searchBox = driver.findElement(By.className("searchBox"));
        searchBox.sendKeys("ab");
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.findElements(By.className("searchBox")).size() > 0;
            }
        });

        try{ Thread.sleep(2000); } catch(Exception e){}
        // conceptList results
        defaultWait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver input) {
                return input.findElements(By.className("conceptList")).size() > 0;
            }
        });

        try{ Thread.sleep(2000); } catch(Exception e){}
        List<WebElement> parents = driver.findElements(By.className("conceptList"));
        WebElement parent = parents.get(parents.size()-1); // one in the main page, one in the 'popup'

        List<WebElement> resultRowsElement = parent.findElements(By.tagName("tr"));
        int changed = 0;

        for(int i = 0; i < resultRowsElement.size(); i++){
            WebElement row =  resultRowsElement.get(i);

            try{ Thread.sleep(2000); } catch(Exception e){}
            row.findElements(By.tagName("input")).get(0).click();
            changed++;
            if(changed > 2){
                break;
            }
        }

        try{ Thread.sleep(2000); } catch(Exception e){}
        WebElement footer = driver.findElements(By.className("dialogFooter")).get(0);
        footer.findElements(By.tagName("button")).get(0).click();
    }


    public void editExistingProposal() {
        driver.findElements(By.tagName("button")).get(1).click();
    }

    public void saveNewProposal() {
        // need to look for proper button. adding concepts spoils this
        try{ Thread.sleep(2000); } catch(Exception e){}
        for(WebElement e : driver.findElements(By.tagName("button"))){
            if(e.getAttribute("ng-click").equals("add()")){
                System.out.println("found add()");
                e.click();
                break;
            }
        }
        //driver.findElements(By.tagName("button")).get(1).click();

        // no alert box now??
        //defaultWait.until(ExpectedConditions.alertIsPresent());
        // Before you try to switch to the so given alert, he needs to be present.

        //Alert alert = driver.switchTo().alert();
        //alert.accept();
    }
}
