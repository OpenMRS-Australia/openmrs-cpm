package org.openmrs.module.conceptpropose.functionaltest.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openmrs.module.conceptpropose.pagemodel.AdminPage;
import org.openmrs.module.conceptpropose.pagemodel.CreateProposalPage;
import org.openmrs.module.conceptpropose.pagemodel.MonitorProposalsPage;
import org.openmrs.module.conceptpropose.pagemodel.SettingsPage;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProposalStepDefs {
    private CreateProposalPage createProposalPage;
    private MonitorProposalsPage monitorProposalsPage;
    private AdminPage adminPage;

    @Given("^I have a saved draft proposal$")
     public void navigate_to_page() throws IOException {

    }

    @When("^I submit the proposal$")
    public void submit_proposal(){

    }

    @Then("^the proposal is sent to the dictionary manager$")
    public void check_the_dictionary_manager() throws InterruptedException{
        Thread.sleep(5000);

    }

    @Given("^I have a new proposal with all necessary details$")
     public void fill_a_new_proposal() throws IOException {
        loadPage();
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


    public void loadPage() throws IOException {
        Login login = new Login();
        adminPage = login.login();
        createProposalPage= adminPage.navigateToCreateProposalPage();
    }
}
