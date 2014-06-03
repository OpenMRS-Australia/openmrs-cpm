package org.openmrs.module.conceptpropose.functionaltest.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openmrs.module.conceptpropose.pagemodel.AdminPage;
import org.openmrs.module.conceptpropose.pagemodel.CreateProposalPage;
import org.openmrs.module.conceptpropose.pagemodel.ManageProposalsPage;
import org.openmrs.module.conceptpropose.pagemodel.QueryBrowserPage;

import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ManageProposalsStepDefs {

	private Login login;
	private AdminPage adminPage;
	private ManageProposalsPage manageProposalsPage;
	private String currName;

	@Given("that I am logged in as a Local Admin")
	public void login() throws IOException {
		login = new Login();
		adminPage = login.login();
	}

	@And("that at least one proposal has been submitted")
	public void submittedProposalPrecondition() {
		final CreateProposalPage createProposalLink = adminPage.navigateToCreateProposalPage();
		currName = UUID.randomUUID().toString();
		createProposalLink.enterNewProposal(currName, "bbunny@gmail.com", "What's up doc?");
		createProposalLink.navigateToAddConceptDialog();
		createProposalLink.enterNewConcept("X-RAY, ARM", 1);
		createProposalLink.submitProposal();
	}

	@When("I open the Manage Proposal page")
	public void navigateToManageProposalPage() {
		adminPage.navigateToAdminPage();
		manageProposalsPage = adminPage.navigateToManageProposals();
	}

	@Then("all proposals that have been submitted are displayed")
	public void checkAllSubmittedProposalsAreDisplayed() {
		final boolean b = manageProposalsPage.checkIfProposalIsSubmitted(currName);
		assertThat(b, is(true));
	}

	@And("that at least one proposal has been saved as a draft")
	public void draftProposalPrecondition() {
		final CreateProposalPage createProposalLink = adminPage.navigateToCreateProposalPage();
		currName = UUID.randomUUID().toString();
		createProposalLink.enterNewProposal(currName, "bbunny@gmail.com", "What's up doc?");
		createProposalLink.navigateToAddConceptDialog();
		createProposalLink.enterNewConcept("X-RAY, ARM", 1);
		createProposalLink.saveNewProposal();
	}

	@Then("all proposals that have been saved as a draft are displayed")
	public void checkAllDraftProposalsAreDisplayed() {
		manageProposalsPage.checkIfProposalIsSaved(currName);
	}

	@And("there are no saved drafts and no submitted proposals")
	public void noProposalsPrecondition() throws IOException {
		final QueryBrowserPage queryBrowserPage = new QueryBrowserPage();
		queryBrowserPage.removeAllProposals();
	}

	@Then("there are no proposals displayed")
	public void checkNoProposalsDisplayed() {
		final int proposalCount = manageProposalsPage.getProposalCount();

		assertThat(proposalCount, is(0));
	}

	@Then("a message is displayed \"No proposals have been created\"")
	public void checkNoProposalMsg() {
		final boolean msgDisplayed = manageProposalsPage.checkNoProposalsMessageIs("No proposals have been created");

		assertThat(msgDisplayed, is(true));
	}
}
