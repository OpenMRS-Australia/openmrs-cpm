package org.openmrs.module.conceptreview.pagemodel;

import org.openmrs.module.conceptpropose.pagemodel.BaseCpmPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ReviewConceptPage extends BaseCpmPage {

    public ReviewConceptPage(WebDriver driver) {
        super(driver);
    }

    public ReviewProposalPage acceptConcept(){
        getElementByAttribute("button","ng-click", "conceptCreated()").click();
        return new ReviewProposalPage(driver);
    }
    public ReviewProposalPage rejectConcept(){
        getElementByAttribute("button","ng-click", "conceptRejected()").click();
        return new ReviewProposalPage(driver);
    }
    public ReviewProposalPage markConceptAsExisted(){
        getElementByAttribute("button","ng-click", "conceptExists()").click();
        return new ReviewProposalPage(driver);
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
