package org.openmrs.module.conceptpropose.functionaltest.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.openmrs.module.conceptpropose.pagemodel.SettingsPage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SettingsStepDefs {

	private SettingsPage page;
	private String originalUrl;
	private String originalUsername;
	private String originalPassword;

	private void retrieveOriginalSettings() {
		originalUrl = page.getUrl();
		originalUsername = page.getUsername();
		originalPassword = page.getPassword();
	}

	private void restoreOriginalSettings() throws IOException {
		page.enterSettings(originalUrl, originalUsername, originalPassword);
	}

    @Given("^I'm on the Concept Proposal Settings page$")
    public void navigate_to_page() throws IOException {
    	loadPage();
    }

    @When("^I enter the settings for a dictionary$")
    public void i_enter_the_settings_for_a_dictionary() {
		retrieveOriginalSettings();
		page.enterSettings("http://random.url:8080/openmrs", "some-username", "some-password");
	}

    @When("^I refresh the page$")
    public void i_refresh_the_page() {
    	SeleniumDriver.getDriver().navigate().refresh();
    }

    @Then("^those settings should still be there$")
    public void those_settings_should_still_be_there() throws IOException {
        assertThat(page.getUrl(), equalTo("http://random.url:8080/openmrs"));
		assertThat(page.getUsername(), equalTo("some-username"));
		assertThat(page.getPassword(), equalTo("some-password"));
		restoreOriginalSettings();
	}

	private void loadPage() throws IOException {
        Login login = new Login();
		page = login.login().navigateToSettings();
	}
}
