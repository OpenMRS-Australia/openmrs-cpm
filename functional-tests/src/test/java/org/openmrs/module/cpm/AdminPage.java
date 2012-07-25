package org.openmrs.module.cpm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AdminPage {

	private static final String createProposalUrl = "/openmrs/module/cpm/concept.form";
	private static final String monitorProposalsUrl = "/openmrs/module/cpm/monitor.list";

	private String username;
	private String password;
	private String adminPageUrl;

	private final RemoteWebDriver driver;

	public AdminPage(final RemoteWebDriver driver) {
		this.driver = driver;
	}

	public void navigateToAdminPage() {
		driver.get(adminPageUrl);
		login();
	}

	/**
	 * Checks if there's one and only one create proposal link
	 *
	 * @return True if the link exists, false otherwise
	 */
	public boolean hasCreateProposalLink() {
		return driver.findElements(By.cssSelector("a[href=\"" + createProposalUrl  + "\"]")).size() == 1;
	}

	public boolean hasMonitorProposalsLink() {
		return driver.findElements(By.cssSelector("a[href=\"" + monitorProposalsUrl + "\"]")).size() == 1;
	}

	private void login() {
		final WebElement usernameElement = driver.findElement(By.name("uname"));
		usernameElement.sendKeys(username);
		final WebElement passwordElement = driver.findElement(By.name("pw"));
		passwordElement.sendKeys(password);
		passwordElement.submit();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getAdminPageUrl() {
		return adminPageUrl;
	}

	public void setAdminPageUrl(final String adminPageUrl) {
		this.adminPageUrl = adminPageUrl;
	}
}
