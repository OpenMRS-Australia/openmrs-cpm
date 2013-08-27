package org.openmrs.module.cpm.functionaltest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.cpm.pagemodel.AdminPage;
import org.openmrs.module.cpm.pagemodel.SettingsPage;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SettingsPageDefs {
	
	private AdminPage adminPage;
	private SettingsPage page;
	private static FirefoxDriver driver = new FirefoxDriver();
	private String openmrsUrl = "openmrs";
	
	

    @Given("^I'm on the ([^\"]*) page$")
    public void navigate_to_page(String page) throws IOException {
    	load();
    }

    @When("^I enter the settings for a dictionary$")
    public void i_enter_the_settings_for_a_dictionary() {
    	page.enterSettings("http://localhost:8080/some-openmrs-context", "someusername", "somepassword");
    }
    
    @When("^I refresh the page$")
    public void i_refresh_the_page() {
    	driver.navigate().refresh();
    }

    @Then("^those settings should still be there$")
    public void those_settings_should_still_be_there() {
    	assertThat(page.getUrl(), equalTo("http://localhost:8080/some-openmrs-context"));
		assertThat(page.getUsername(), equalTo("someusername"));
		assertThat(page.getPassword(), equalTo("somepassword"));
    }
    
    

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



	@After
	public static void afterClass() {
		driver.quit();
	}
    
    
    
    
}
