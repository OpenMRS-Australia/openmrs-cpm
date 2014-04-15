package org.openmrs.module.conceptpropose.functionaltest.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.sf.saxon.exslt.Date;
import net.sf.saxon.exslt.Random;
import org.openmrs.module.conceptpropose.pagemodel.AdminPage;
import org.openmrs.module.conceptpropose.pagemodel.CreateProposalPage;
import org.openmrs.module.conceptpropose.pagemodel.MonitorProposalsPage;
import org.openmrs.module.conceptpropose.pagemodel.SettingsPage;
import org.openqa.selenium.browserlaunchers.Sleeper;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ProposalStepDefs {
    private CreateProposalPage createProposalPage;
    private MonitorProposalsPage monitorProposalsPage;
    private AdminPage adminPage;


    // TODO: how to make this better instead of waiting 1 second?
    // vagrant on OSX seems to be able to do without the sleep (most of the time?)
    // vagrant on Windows 8.1, the sleep seems necessary to pass tests
    // clearly something is inconsistent and is something to look into
    private int sleep_length = 000;
    private String generatedName = "";
    private int oldConceptCount = 0;
    private String generatedComment = "";

    @Given("^I have a saved draft proposal$")
    public void navigate_to_page() throws IOException, InterruptedException {
        login();
        loadNewProposalPage();
        if(sleep_length != 0) Thread.sleep(sleep_length);
        adminPage.navigateToAdminPage();
        if(sleep_length != 0) Thread.sleep(sleep_length);
        loadProposalMonitorPage();
        monitorProposalsPage.navigateToDraftProposal("");
    }
    @Given("^I have a saved draft proposal with zero concepts")
    public void saved_draft_proposal_with_zero_concepts() throws IOException, InterruptedException {
        login();
        loadNewProposalPage();
        if(sleep_length != 0) Thread.sleep(sleep_length);
        adminPage.navigateToAdminPage();
        if(sleep_length != 0) Thread.sleep(sleep_length);
        loadProposalMonitorPage();
        monitorProposalsPage.navigateToDraftProposalWithNoConcepts("");
    }

    @Given("^I have a saved draft proposal with at least 1 concept")
    public void saved_draft_proposal_with_at_least_1_concept() throws IOException, InterruptedException {
        login();
        loadNewProposalPage();
        adminPage.navigateToAdminPage();
        loadProposalMonitorPage();
        // find proposal with more than 1
        monitorProposalsPage.navigateToDraftProposalWithConcepts("");
    }

    @Given("^I have a saved draft proposal for deletion")
    public void navigate_to_proposal_for_deletion() throws IOException, InterruptedException {
        loadProposalMonitorPage();
        System.out.println("Delete " + generatedName);
        monitorProposalsPage.navigateToDraftProposal(generatedName);
    }
    @When("^I submit the proposal$")
    public void submit_proposal() throws IOException, InterruptedException {
        if(sleep_length != 0) Thread.sleep(sleep_length);
        createProposalPage.submitProposal();
    }

    @Then("^the proposal is sent to the dictionary manager$")
    public void check_the_dictionary_manager() throws IOException, InterruptedException{
        loadProposalMonitorPage();
        assertThat(monitorProposalsPage.findProposalStatus(generatedName), equalTo("Submitted"));
    }

    @When("^I change the details and save$")
    public void edit_existing_proposal() throws IOException, InterruptedException {
        if(sleep_length != 0) Thread.sleep(sleep_length);
        generatedName = newRandomString();
        System.out.println("Generated email " + generatedName);
        createProposalPage.enterNewProposal(generatedName, "email_edit@example.com", "Some Comments Edit");
        if(sleep_length != 0) Thread.sleep(sleep_length);
        createProposalPage.editExistingProposal();
    }
    @When("I add a concept and save")
    public void add_concept_to_draft_proposal(){
        oldConceptCount = createProposalPage.getNumberOfConcepts();
        System.out.println("Old concept count: " + oldConceptCount);
        generatedName = newRandomString();
        System.out.println("New name: " + generatedName);
        createProposalPage.enterNewProposal(generatedName, "email_edit@example.com", "Some Comments Edit");
        createProposalPage.navigateToAddConceptDialog();
        // TODO: need to ensure that this hasn't been previously entered
        createProposalPage.enterNewConcept("ba", 1);
        createProposalPage.enterNewConceptComment("This is ab");
        createProposalPage.editExistingProposal();
        monitorProposalsPage.waitUntilFullyLoaded();
    }

    @Then("the proposal is stored with the added concept details")
    public void check_added_concept_details() throws IOException{
        loadProposalMonitorPage();
        assertThat(monitorProposalsPage.getProposalConceptCount(generatedName), equalTo(oldConceptCount+1));
    }
    @When("I change the first concept comment")
    public void edit_concept(){
        generatedName = newRandomString();
        createProposalPage.enterNewProposal(generatedName, "email_edit@example.com", "Some Comments Edit");
        generatedComment = newRandomString();
        createProposalPage.enterNewConceptComment(generatedComment);
        createProposalPage.editExistingProposal();
        monitorProposalsPage.waitUntilFullyLoaded();
    }
    @Then("the concept comment is saved")
    public void check_edited_concept_details(){
        monitorProposalsPage.navigateToDraftProposal(generatedName);
        assertThat(createProposalPage.getConceptComment(), equalTo(generatedComment));

    }

    @Then("^the proposal is stored with the new details$")
    public void check_the_edited_details() throws InterruptedException, IOException {
        loadProposalMonitorPage();
        monitorProposalsPage.navigateToDraftProposal(generatedName);
        // TODO details of concepts added?
        assertThat(createProposalPage.getName(), equalTo(generatedName));
        assertThat(createProposalPage.getComment(), equalTo("Some Comments Edit"));
        assertThat(createProposalPage.getEmail(), equalTo("email_edit@example.com"));
    }

    @Given("^I have a new proposal with all necessary details$")
    public void fill_a_new_proposal() throws IOException {
        login();
        loadNewProposalPage();
        createProposalPage.enterNewProposal("Some Name", "email@example.com", "Some Comments");
    }

    @When("^I save$")
    public void save_new_proposal(){
        createProposalPage.saveNewProposal();
    }

    @Then("^the proposal is stored with the details$")
    public void check_the_details() throws InterruptedException, IOException {
        loadProposalMonitorPage();
        // TODO: need to verify email, concept added and concept comment
        assertThat(monitorProposalsPage.getLastProposalName(), equalTo("Some Name"));
        assertThat(monitorProposalsPage.getLastProposalDescription(), equalTo("Some Comments"));
        // assertThat(monitorProposalsPage.getConceptCount(), equalTo("1"));
    }

    @When("^I delete a concept")
    public void delete_concept() {
        oldConceptCount = createProposalPage.getNumberOfConcepts();
        generatedName = newRandomString();
        createProposalPage.enterNewProposal(generatedName, "email_edit@example.com", "Some Edit Comment");
        createProposalPage.deleteExistingConcept();
        createProposalPage.saveNewProposal();
        monitorProposalsPage.waitUntilFullyLoaded();
    }
    @Then("^the concept is deleted")
    public void concept_is_deleted() throws IOException {
        loadProposalMonitorPage();
        assertThat(monitorProposalsPage.getConceptCount(), equalTo(""+(oldConceptCount-1)));
    }

    @When("I delete the proposal")
    public void delete_proposal() {
        try{ Thread.sleep(2000); }catch(Exception e){}
        createProposalPage.deleteProposal();
        monitorProposalsPage.waitUntilFullyLoaded();
    }
    @Then("the proposal is deleted")
    public void proposal_is_deleted() throws IOException{
        loadProposalMonitorPage();
        assertNull(monitorProposalsPage.findDraftProposalByName(generatedName));
    }
    private void loadProposalMonitorPage() throws IOException {
        monitorProposalsPage = adminPage.navigateToMonitorProposals();
        monitorProposalsPage.waitUntilFullyLoaded();
    }

    private void loadNewProposalPage() throws IOException {
        createProposalPage= adminPage.navigateToCreateProposalPage();
    }
    private void login()  throws IOException{
        Login login = new Login();
        adminPage = login.login();
    }

    private String newRandomString(){
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }
}
