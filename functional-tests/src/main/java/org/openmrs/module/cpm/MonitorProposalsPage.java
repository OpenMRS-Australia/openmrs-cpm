package org.openmrs.module.cpm;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MonitorProposalsPage {

	private final RemoteWebDriver driver;

	public MonitorProposalsPage(RemoteWebDriver driver) {
		this.driver = driver;
	}

	public String getHeaderText() {
		return driver.findElement(By.id("headerText")).getText();
	}

}
