package org.openmrs.module.conceptpropose.functionaltest.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.conceptpropose.pagemodel.AdminPage;
import org.openmrs.module.conceptpropose.pagemodel.SettingsPage;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SettingsStepDefs {
	

	private SettingsPage page;

	
    @Given("^I'm on the Concept Proposal Settings page$")
    public void navigate_to_page() throws IOException {
    	loadPage();
    }

    @When("^I enter the settings for a dictionary$")
    public void i_enter_the_settings_for_a_dictionary() {
    	page.enterSettings("http://localhost:8080/some-openmrs-context", "someusername", "somepassword");
    }
    
    @When("^I refresh the page$")
    public void i_refresh_the_page() {
    	SeleniumDriver.getDriver().navigate().refresh();
    }

    @Then("^those settings should still be there$")
    public void those_settings_should_still_be_there() {
    	assertThat(page.getUrl(), equalTo("http://localhost:8080/some-openmrs-context"));
		assertThat(page.getUsername(), equalTo("someusername"));
		assertThat(page.getPassword(), equalTo("somepassword"));
    }
    
    

	public void loadPage() throws IOException {
        Login login = new Login();
		page = login.login().navigateToSettings();
	}
    
}
