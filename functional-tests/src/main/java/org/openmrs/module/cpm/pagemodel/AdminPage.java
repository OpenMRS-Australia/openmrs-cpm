package org.openmrs.module.cpm.pagemodel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AdminPage {
	private static final String createProposalUrl = "/module/cpm/proposals.list#/edit";
	private static final String monitorProposalsUrl = "/module/cpm/proposals.list";

	private final RemoteWebDriver driver;
    private String openmrsUrl;

    public AdminPage(final RemoteWebDriver driver, final String adminPageUrl, String openmrsUrl) {
        this.openmrsUrl = openmrsUrl;
		this.driver = driver;
		driver.navigate().to(adminPageUrl);
	}

	public WebElement getCreateProposalLink() {
		return driver.findElement(By.cssSelector("a[href=\"/" + openmrsUrl + createProposalUrl  + "\"]"));
	}

	public WebElement getMonitorProposalsLink() {
		return driver.findElement(By.cssSelector("a[href=\"/"  + openmrsUrl + monitorProposalsUrl + "\"]"));
	}

	private WebElement getLogoutLink() {
		return driver.findElement(By.linkText("Log out"));
	}

	public void login(final String username, final String password) {
		final WebElement usernameElement = driver.findElement(By.name("uname"));
		usernameElement.sendKeys(username);
		final WebElement passwordElement = driver.findElement(By.name("pw"));
		passwordElement.sendKeys(password);
		passwordElement.submit();
	}

	public void logout() {
		getLogoutLink().click();
	}

	public CreateProposalPage navigateToCreateProposalPage() {
		getCreateProposalLink().click();
		return new CreateProposalPage(driver);
	}

	public MonitorProposalsPage navigateToMonitorProposals() {
		 getMonitorProposalsLink().click();
		return new MonitorProposalsPage(driver);
	}
}
