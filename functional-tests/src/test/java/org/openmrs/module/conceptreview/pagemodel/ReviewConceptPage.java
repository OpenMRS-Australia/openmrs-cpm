package org.openmrs.module.conceptreview.pagemodel;

import org.openmrs.module.conceptpropose.pagemodel.BaseCpmPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class ReviewConceptPage extends BaseCpmPage {

    public ReviewConceptPage(WebDriver driver) {
        super(driver);
    }

    public ReviewProposalPage acceptConcept(){
        getElementByAttribute("button","ng-click", "conceptCreated()").click();
        enterNewConcept("ba",1);
        return new ReviewProposalPage(driver);
    }
    public ReviewProposalPage rejectConcept(){
        getElementByAttribute("button","ng-click", "conceptRejected()").click();
        return new ReviewProposalPage(driver);
    }
    public ReviewProposalPage markConceptAsExisted(){
        getElementByAttribute("button","ng-click", "conceptExists()").click();
        enterNewConcept("ba", 1);
        return new ReviewProposalPage(driver);
    }
    public WebElement findVisibleElement(WebDriver driver, By by)
    {
        List<WebElement> elements = driver.findElements(by);
        for(WebElement element :elements) {
            if(element.isDisplayed())
                return element;
        }
        return null;
    }
    public void enterNewConcept(String conceptToSearch, int numberToAdd) {
        // if multiple concepts are processed at one go, there will be multiple concept search boxes
        // so just use the visible one
        final WebElement addConceptContainer = findVisibleElement(driver, By.cssSelector(".resultsContainer"));
        final WebElement searchBox = findVisibleElement(driver, By.className("searchBox"));
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
        WebElement footer = findVisibleElement(driver, (By.className("dialogFooter")));
        WebElement addConceptButton = getElementByAttribute(footer, "button", "ng-click", "add()");
        addConceptButton.click();
    }
	private WebElement getAddCommentButton()
	{
		return getElementByAttribute("button","ng-click", "saveReviewComment()");
	}
	private WebElement getCommentBox()
	{
		return getElementByAttribute("textarea", "ng-model", "concept.reviewComment");
	}
	public ReviewConceptPage addComment(String comment){
		return addComment(comment, false);
	}
	public ReviewConceptPage addComment(String comment, boolean clearBox){
		WebElement commentBox = getCommentBox();
		if(clearBox) commentBox.clear();
		commentBox.sendKeys(comment);
		getAddCommentButton().click();

		// unsure how to wait till AJAX completes. so just wait 2 seconds before reloading.
		// could result in false failing tests if refreshes page before AJAX saves on server side
		try { Thread.sleep(5000); } catch(Exception e){System.out.println("Sleep ex: " + e.getMessage()); }
		refresh();
		return new ReviewConceptPage(driver);
	}
	public String getComment(){
		return getCommentBox().getAttribute("value");
	}
	public void refresh(){
		driver.navigate().refresh();
		waitUntilFullyLoaded();
	}
	public void waitUntilFullyLoaded(){
		final WebElement loadingIcon = driver.findElement(By.className("loading"));
		defaultWait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver input) {
				return !loadingIcon.isDisplayed();
			}
		});
	}
}
