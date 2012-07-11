package org.openmrs.module.cpm;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AdminPage {

	private static final String USERNAME = "Admin";
	private static final String PASSWORD = "Admin123";
	private final RemoteWebDriver driver;
	private final String adminPageUrl = "http://localhost:8080/openmrs/admin";
	private final String createProposalUrl = "/openmrs/module/cpm/concept.form";

	public AdminPage(final RemoteWebDriver driver) {
		this.driver = driver;
		driver.get(adminPageUrl);
		login();
	}

	/**
	 * not really sure if this ist ja the right way to wrap selenium in a page object
	 *
	 * @return True if the link exists, false otherwise
	 */
	public boolean hasCreateProposalLink() {
		try {
			driver.findElement(By.cssSelector("a[href=\"" + createProposalUrl  + "\"]"));
			return true;
		} catch (final NoSuchElementException e) {
			return false;
		}
	}

	private void login() {
		final WebElement username = driver.findElement(By.name("uname"));
		username.sendKeys(USERNAME);
		final WebElement password = driver.findElement(By.name("pw"));
		password.sendKeys(PASSWORD);
		password.submit();
	}
}
