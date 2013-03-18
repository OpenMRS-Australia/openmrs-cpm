package org.openmrs.module.cpm.functionaltest;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.cpm.pagemodel.AdminPage;
import org.openmrs.module.cpm.pagemodel.CreateProposalPage;
import org.openmrs.module.cpm.pagemodel.IncomingProposalsPage;
import org.openmrs.module.cpm.pagemodel.MonitorProposalsPage;
import org.openmrs.module.cpm.pagemodel.SettingsPage;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class TestAdminPageLinks {

	private AdminPage page;
	private static FirefoxDriver driver;
    private String openmrsUrl = "openmrs";

    @BeforeClass
	public static void beforeClass() {
		driver = new FirefoxDriver();
	}

	@Before
	public void load() throws IOException {
		String username;
		String password;
		String adminPageUrl;

		if (StringUtils.isNotBlank(System.getenv("openmrs_username"))) {
			username = System.getenv("openmrs_username");
			password = System.getenv("openmrs_password");

            if (StringUtils.isNotBlank(System.getenv("openmrs_url"))) {
                openmrsUrl = System.getenv("openmrs_url");
            }

            adminPageUrl = String.format("http://%s/%s/admin", System.getenv("openmrs_server"), openmrsUrl);
		} else {
			final Properties p = new Properties();
			final InputStream is = getClass().getResourceAsStream("/config.properties");

			p.load(new InputStreamReader(is));
			username = p.getProperty("username");
			password = p.getProperty("password");
			adminPageUrl = p.getProperty("adminPageUrl");
		}

		page = new AdminPage(driver, adminPageUrl, openmrsUrl);
		page.login(username, password);
	}

	@Test
	public void shouldNavigateToCreateProposalPage() {
		assertNotNull(page.getCreateProposalLink());

		final CreateProposalPage createProposalPage = page.navigateToCreateProposalPage();
		assertEquals("Create Concept Proposal", createProposalPage.getHeaderText());
	}

	@Test
	public void shouldNavigateToMonitorProposalsPage() {
		assertNotNull(page.getMonitorProposalsLink());

		final MonitorProposalsPage monitorProposalsPage = page.navigateToMonitorProposals();
		assertEquals("Manage Concept Proposals", monitorProposalsPage.getHeaderText());
	}

	@Test
	public void shouldNavigateToIncomingProposalsPage() {
		assertNotNull(page.getIncomingProposalsLink());

		final IncomingProposalsPage incomingProposalsPage = page.navigateToIncomingProposals();
		assertEquals("Incoming Concept Proposals", incomingProposalsPage.getHeaderText());
	}

	@Test
	public void shouldNavigateToSettingsPage() {
		assertNotNull(page.getSettingsLink());

		final SettingsPage settingsPage = page.navigateToSettings();
		assertEquals("Concept Proposal Settings", settingsPage.getHeaderText());
	}

	@After
	public void logout() {
		page.logout();
	}

	@AfterClass
	public static void afterClass() {
		driver.quit();
	}

}
