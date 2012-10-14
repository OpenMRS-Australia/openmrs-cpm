package org.openmrs.module.cpm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MonitorProposalsPage {

	private final RemoteWebDriver driver;

	public MonitorProposalsPage(final RemoteWebDriver driver) {
		this.driver = driver;
	}

	public String getHeaderText() {

		// Header text is loaded dynamically so adding a wait
		final WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(final WebDriver d) {
				return d.findElement(By.id("headerText")) != null;
			}
		});

		return driver.findElementById("headerText").getText();
	}

}
