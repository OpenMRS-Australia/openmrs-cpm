package org.openmrs.module.cpm.functionaltest;

import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openmrs.module.cpm.pagemodel.AdminPage;
import org.openmrs.module.cpm.pagemodel.SettingsPage;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TestSettingsPage {

	private AdminPage adminPage;
	private SettingsPage page;
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

		adminPage = new AdminPage(driver, adminPageUrl, openmrsUrl);
		adminPage.login(username, password);
		page = adminPage.navigateToSettings();
	}

	@Test
	public void shouldPersistUrlUsernamePassword() {

		page.enterSettings("http://localhost:8080/some-openmrs-context", "someusername", "somepassword");
		driver.navigate().refresh();

		assertThat(page.getUrl(), equalTo("http://localhost:8080/some-openmrs-context"));
		assertThat(page.getUsername(), equalTo("someusername"));
		assertThat(page.getPassword(), equalTo("somepassword"));
	}

	@AfterClass
	public static void afterClass() {
		driver.quit();
	}
}
