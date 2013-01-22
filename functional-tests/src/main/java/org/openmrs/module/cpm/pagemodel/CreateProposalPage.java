package org.openmrs.module.cpm.pagemodel;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CreateProposalPage {

	private final RemoteWebDriver driver;

	public CreateProposalPage(RemoteWebDriver driver) {
		this.driver = driver;
	}

	public String getHeaderText() {
		return driver.findElement(By.id("headerText")).getText();
	}

}
