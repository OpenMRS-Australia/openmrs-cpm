package org.openmrs.module.conceptreview.pagemodel;

import java.util.List;
import org.openmrs.module.conceptpropose.pagemodel.BaseCpmPage;
import org.openqa.selenium.Alert;
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
		return getElementByAttribute("button","ng-click", "addComment()");
	}
	private List<WebElement> getComments()
	{
		return driver.findElements(By.cssSelector("table.comments .results tr"));
	}
	public int getNumberOfComments()
	{
		List<WebElement> comments = getComments();
		if(comments == null) return 0;
		return getComments().size();
	}
	public String getCommentNameAndEmail(int i){
		return getComments().get(i).findElements(By.tagName("td")).get(1).getText();
	}
	public String getCommentBody(int i){
		return getComments().get(i).findElements(By.tagName("td")).get(2).getText();
	}

	private WebElement getCommentName()
	{
		return getElementByAttribute("input", "ng-model", "concept.newCommentName");
	}
	private WebElement getCommentEmail()
	{
		return getElementByAttribute("input", "ng-model", "concept.newCommentEmail");
	}
	private WebElement getCommentBox()
	{
		return getElementByAttribute("textarea", "ng-model", "concept.newCommentText");
	}
	public ReviewConceptPage addComment(String name, String email, String comment){
		WebElement commentName = getCommentName();
		commentName.sendKeys(name);
		WebElement commentEmail = getCommentEmail();
		commentEmail.sendKeys(email);
		WebElement commentBox = getCommentBox();
		commentBox.sendKeys(comment);
		getAddCommentButton().click();
		clickOkOnDialog();
		return new ReviewConceptPage(driver);
	}
	public void clickOkOnDialog() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
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
