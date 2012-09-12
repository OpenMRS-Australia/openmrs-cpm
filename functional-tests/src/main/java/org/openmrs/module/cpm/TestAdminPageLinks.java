package org.openmrs.module.cpm;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;


public class TestAdminPageLinks {

	private AdminPage page;
	private static FirefoxDriver driver;
	
	@BeforeClass
	public static void beforeClass() {
		driver = new FirefoxDriver();
	}

	@Before
	public void load() throws FileNotFoundException, IOException {
		String username = ""; 
		String password = "";
		String adminPageUrl = "";

		if (StringUtils.isNotBlank(System.getenv("openmrs_username"))) {
			username = System.getenv("openmrs_username");
			password = System.getenv("openmrs_password");
			adminPageUrl = String.format("http://%s/openmrs/admin",System.getenv("openmrs_server"));
		} else {
			final Properties p = new Properties();
			final InputStream is = getClass().getResourceAsStream("/config.properties");
			
			p.load(new InputStreamReader(is));
			username = p.getProperty("username");
			password = p.getProperty("password");
			adminPageUrl = p.getProperty("adminPageUrl");
		}
		
		page = new AdminPage(driver);
		page.setUsername(username);
		page.setPassword(password);
		page.setAdminPageUrl(adminPageUrl);
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
	public void logout() {
		page.logout();
	}

	@AfterClass
	public static void afterClass() {
		driver.quit();
	}

}
