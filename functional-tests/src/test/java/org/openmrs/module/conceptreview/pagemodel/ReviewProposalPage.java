package org.openmrs.module.conceptreview.pagemodel;

import org.openmrs.module.conceptpropose.pagemodel.BaseCpmPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;

public class ReviewProposalPage extends BaseCpmPage {

	public ReviewProposalPage(WebDriver driver) {
		super(driver);
	}

	public HashMap<String, String> getConcepts() {
		HashMap<String, String> concepts = new HashMap<String, String>();
		for (WebElement element : driver.findElements(By.cssSelector(".concept"))) {
			concepts.put("name", element.findElement(By.className("name")).getText());
			concepts.put("class", element.findElement(By.className("class")).getText());
			concepts.put("datatype", element.findElement(By.className("datatype")).getText());
			concepts.put("comments", element.findElement(By.className("comments")).getText());
			concepts.put("status", element.findElement(By.className("status")).getText());
		}

		return concepts;
	}
    public ReviewConceptPage navigateTo(int conceptNumber) {
        driver.findElement(By.cssSelector("#conceptreview .results tr:nth-of-type(" + String.valueOf(conceptNumber) + ")")).click();
        return new ReviewConceptPage(driver);
    }

    public String getConceptStatus(int conceptNumber)
    {
        return driver.findElement(By.cssSelector("#conceptreview .results tr:nth-child(" + conceptNumber + ") td:nth-child(4)")).getText();
    }
}
