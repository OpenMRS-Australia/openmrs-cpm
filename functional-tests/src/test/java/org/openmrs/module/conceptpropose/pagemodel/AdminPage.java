package org.openmrs.module.conceptpropose.pagemodel;
import org.openmrs.module.conceptreview.pagemodel.ReviewProposalsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AdminPage {
	private static final String createProposalUrl = "/module/conceptpropose/proposals.list#/edit";
	private static final String monitorProposalsUrl = "/module/conceptpropose/proposals.list";
	private static final String incomingProposalsUrl = "/module/conceptreview/proposalReview.list";
	private static final String settingsUrl = "/module/conceptpropose/proposals.list#/settings";

	private final RemoteWebDriver driver;
    private String openmrsUrl;
    private String adminPageUrl;

	public AdminPage(final RemoteWebDriver driver, final String adminPageUrl, String openmrsUrl) {
        this.openmrsUrl = openmrsUrl;
        this.adminPageUrl = adminPageUrl;
		this.driver = driver;
        navigateToAdminPage();
	}

	public WebElement getCreateProposalLink() {
		return driver.findElement(By.cssSelector("a[href=\"/" + openmrsUrl + createProposalUrl  + "\"]"));
	}

	public WebElement getManageProposalsLink() {
		return driver.findElement(By.cssSelector("a[href=\"/"  + openmrsUrl + monitorProposalsUrl + "\"]"));
	}

	public WebElement getIncomingProposalsLink() {
		return driver.findElement(By.cssSelector("a[href=\"/"  + openmrsUrl + incomingProposalsUrl + "\"]"));
	}

	public WebElement getSettingsLink() {
		return driver.findElement(By.cssSelector("a[href=\"/"  + openmrsUrl + settingsUrl + "\"]"));
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

    public void navigateToAdminPage() {
        driver.navigate().to(adminPageUrl);
    }

    public CreateProposalPage navigateToCreateProposalPage() {
        getCreateProposalLink().click();
        return new CreateProposalPage(driver);
    }

	public ManageProposalsPage navigateToManageProposals(){
    	getManageProposalsLink().click();
		return new ManageProposalsPage(driver);
	}

	public ReviewProposalsPage navigateToIncomingProposals() {
		getIncomingProposalsLink().click();
		return new ReviewProposalsPage(driver);
	}

	public SettingsPage navigateToSettings() {
		getSettingsLink().click();
		return new SettingsPage(driver);
	}
}
