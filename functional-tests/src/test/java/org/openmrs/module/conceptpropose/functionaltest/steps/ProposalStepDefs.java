package org.openmrs.module.conceptpropose.functionaltest.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openmrs.module.conceptpropose.pagemodel.AdminPage;
import org.openmrs.module.conceptpropose.pagemodel.CreateProposalPage;
import org.openmrs.module.conceptpropose.pagemodel.MonitorProposalsPage;
import org.openmrs.module.conceptpropose.pagemodel.SettingsPage;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProposalStepDefs {
    private CreateProposalPage createProposalPage;
    private MonitorProposalsPage monitorProposalsPage;
    private AdminPage adminPage;


    // TODO: how to make this better instead of waiting 1 second?
    // vagrant on OSX seems to be able to do without the sleep (most of the time?)
    // vagrant on Windows 8.1, the sleep seems necessary to pass tests
    // clearly something is inconsistent and is something to look into
    private int sleep_length = 2000;

    @Given("^I have a saved draft proposal$")
    public void navigate_to_page() throws IOException, InterruptedException {
        login();
        loadNewProposalPage();
        if(sleep_length != 0) Thread.sleep(sleep_length);
        adminPage.navigateToAdminPage();
        if(sleep_length != 0) Thread.sleep(sleep_length);
        loadProposalMonitorPage();
    }

    @When("^I submit the proposal$")
    public void submit_proposal() throws IOException, InterruptedException {
        monitorProposalsPage.goToEditPageOfLastItem();
        if(sleep_length != 0) Thread.sleep(sleep_length);
        createProposalPage.submitProposal();
    }

    @Then("^the proposal is sent to the dictionary manager$")
    public void check_the_dictionary_manager() throws InterruptedException{
        if(sleep_length != 0) Thread.sleep(sleep_length);
        assertThat(monitorProposalsPage.getLastProposalStatus(), equalTo("Submitted"));
    }

    @When("^I change the details and save$")
    public void edit_existing_proposal() throws IOException, InterruptedException {
        monitorProposalsPage.goToEditPageOfLastItem();
        if(sleep_length != 0) Thread.sleep(sleep_length);
        createProposalPage.enterNewProposal("Some Name Edit", "email_edit@example.com", "Some Comments Edit");
        if(sleep_length != 0) Thread.sleep(sleep_length);
        createProposalPage.editExistingProposal();
    }

    @Then("^the proposal is stored with the new details$")
    public void check_the_edited_details() throws InterruptedException, IOException {
        if(sleep_length != 0) Thread.sleep(sleep_length);
        loadProposalMonitorPage();
        if(sleep_length != 0) Thread.sleep(sleep_length);
        // TODO how to check email has changed?
        assertThat(monitorProposalsPage.getLastProposalName(), equalTo("Some Name Edit"));
        assertThat(monitorProposalsPage.getLastProposalDescription(), equalTo("Some Comments Edit"));
    }

    @Given("^I have a new proposal with all necessary details$")
    public void fill_a_new_proposal() throws IOException {
        login();
        loadNewProposalPage();
        createProposalPage.enterNewProposal("Some Name","email@example.com","Some Comments");
    }

    @When("^I save$")
    public void save_new_proposal(){
        createProposalPage.saveNewProposal();
    }

    @Then("^the proposal is stored with the details$")
    public void check_the_details() throws InterruptedException, IOException {
        loadProposalMonitorPage();
        assertThat(monitorProposalsPage.getLastProposalName(), equalTo("Some Name"));
        assertThat(monitorProposalsPage.getLastProposalDescription(), equalTo("Some Comments"));
    }

    private void loadProposalMonitorPage() throws IOException {
        monitorProposalsPage = adminPage.navigateToMonitorProposals();
    }

    private void loadNewProposalPage() throws IOException {
        createProposalPage= adminPage.navigateToCreateProposalPage();
    }
    private void login()  throws IOException{
        Login login = new Login();
        adminPage = login.login();
    }

}
