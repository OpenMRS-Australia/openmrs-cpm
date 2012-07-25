package org.openmrs.module.cpm;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;


public class TestAdminPageLinks {

	private AdminPage page;
	private final FirefoxDriver driver = new FirefoxDriver();

	@Before
	public void load() throws FileNotFoundException, IOException {

		final Properties p = new Properties();
		final InputStream is = getClass().getResourceAsStream("/config.properties");

		p.load(new InputStreamReader(is));

		page = new AdminPage(driver);
		page.setUsername(p.getProperty("username"));
		page.setPassword(p.getProperty("password"));
		page.setAdminPageUrl(p.getProperty("adminPageUrl"));
		page.navigateToAdminPage();
	}

	@Test
	public void testCreateProposalLink() {
		assertTrue(page.hasCreateProposalLink());
	}

	@Test
	public void testMonitorProposalsLink() {
		assertTrue(page.hasMonitorProposalsLink());
	}

	@After
	public void dasIstJaKaputt() {
		driver.quit();
	}

}
