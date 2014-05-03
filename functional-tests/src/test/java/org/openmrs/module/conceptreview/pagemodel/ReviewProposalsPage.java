package org.openmrs.module.conceptreview.pagemodel;

import org.openmrs.module.conceptpropose.pagemodel.BaseCpmPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReviewProposalsPage extends BaseCpmPage {

	public ReviewProposalsPage(WebDriver driver) {
		super(driver);
	}

	public HashMap<String, String> getProposalList() {
		HashMap<String, String> proposals = new HashMap<String, String>();
		final List<WebElement> rows = driver.findElements(By.cssSelector("#proposalReviewList .proposal"));
		for (WebElement row : rows) {
//			descriptions.put("status", row.findElement(By.cssSelector(".status")).getText());
			proposals.put("description", row.findElement(By.cssSelector(".description")).getText());
		}
		return proposals;
	}

	public ReviewProposalPage navigateTo(int proposalNumber) {
		driver.findElement(By.cssSelector("#proposalReviewList .proposal:nth-of-type(" + String.valueOf(proposalNumber) + ")")).click();
		return new ReviewProposalPage(driver);
	}
}
