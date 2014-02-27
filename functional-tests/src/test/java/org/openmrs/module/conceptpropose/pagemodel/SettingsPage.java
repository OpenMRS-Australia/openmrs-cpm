package org.openmrs.module.conceptpropose.pagemodel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class SettingsPage extends BaseCpmPage {

	private By urlSelector = By.id("url");

	public SettingsPage(final WebDriver driver) {
		super(driver);
	}

	public void enterSettings(final String url, final String username, final String password) {

		defaultWait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver input) {
				return input.findElements(urlSelector).size() > 0;
			}
		});

		final WebElement urlElement = driver.findElement(urlSelector);
		urlElement.clear();
		urlElement.sendKeys(url);

		final WebElement usernameElement = driver.findElement(By.id("username"));
		usernameElement.clear();
		usernameElement.sendKeys(username);

		final WebElement passwordElement = driver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys(password);

		driver.findElement(By.tagName("button")).click();
	}

	public String getUrl() {
		defaultWait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver input) {
				return input.findElements(urlSelector).size() > 0;
			}
		});

		return driver.findElement(By.id("url")).getAttribute("value");
	}

	public String getUsername() {
		defaultWait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver input) {
				return input.findElements(urlSelector).size() > 0;
			}
		});

		return driver.findElement(By.id("username")).getAttribute("value");
	}

	public String getPassword() {
		defaultWait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver input) {
				return input.findElements(urlSelector).size() > 0;
			}
		});

		return driver.findElement(By.id("password")).getAttribute("value");
	}
}
