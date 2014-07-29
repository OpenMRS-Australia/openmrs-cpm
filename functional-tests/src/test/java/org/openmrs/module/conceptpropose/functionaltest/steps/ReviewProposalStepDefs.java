package org.openmrs.module.conceptpropose.functionaltest.steps;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openmrs.module.conceptpropose.pagemodel.AdminPage;
import org.openmrs.module.conceptpropose.pagemodel.QueryBrowserPage;
import org.openmrs.module.conceptreview.pagemodel.ReviewConceptPage;
import org.openmrs.module.conceptreview.pagemodel.ReviewProposalPage;
import org.openmrs.module.conceptreview.pagemodel.ReviewProposalsPage;

import java.io.IOException;
import java.util.UUID;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.matchers.JUnitMatchers.containsString;

public class ReviewProposalStepDefs {
    private Login login;
    private AdminPage adminPage;
    private ReviewProposalsPage reviewProposalsPage;
    private ReviewProposalPage reviewProposalPage;
    private ReviewConceptPage reviewConceptPage;
    private String currDescription;

    @Given("that I am logged in as Dictionary Manager")
    public void that_I_am_logged_in_as_Dictionary_Manager() throws IOException {
        login = new Login();
        adminPage = login.login();
    }

    @And("^that a proposal has just been submitted$")
    public void that_a_proposal_has_just_been_submitted() throws IOException {
        final QueryBrowserPage queryBrowserPage = new QueryBrowserPage();
        queryBrowserPage.removeAllProposalsOnReviewModule();
        currDescription = UUID.randomUUID().toString();
        queryBrowserPage.createSubmittedProposalOnReviewModule(currDescription);
    }

    @And("^I open the Review Proposals page$")
    public void I_navigate_to_the_proposal_s_review_page() throws Throwable {
        adminPage.navigateToAdminPage();
        reviewProposalsPage = adminPage.navigateToIncomingProposals();
    }

    @Then("all proposals that have been submitted for review are displayed")
    public void all_proposals_that_have_been_submitted_are_displayed(){
        final boolean b = reviewProposalsPage.checkIfProposalIsSubmitted(currDescription);
        assertThat(b, is(true));
    }


    @And("^I click on a proposal to be reviewed$")
    public void I_click_on_a_proposal_to_be_reviewed() throws Throwable {
        reviewProposalPage = reviewProposalsPage.navigateTo(1);
    }

    @And("^I mark a concept as accepted, rejected or \"([^\"]*)\"$")
    public void I_mark_a_concept_as_accepted_rejected_or(String arg1) {
        reviewConceptPage = reviewProposalPage.navigateTo(1);
        reviewConceptPage.rejectConcept();
        reviewConceptPage = reviewProposalPage.navigateTo(2);
        reviewConceptPage.acceptConcept();
        reviewConceptPage = reviewProposalPage.navigateTo(3);
        reviewConceptPage.markConceptAsExisted();
    }

    @Then("^the concept's status should be updated accordingly$")
    public void the_concept_s_status_should_be_updated_accordingly(){
        final String conceptStatusRejected = reviewProposalPage.getConceptStatus(1);
        assertThat(conceptStatusRejected, containsString("Rejected"));
        final String conceptStatusAccept = reviewProposalPage.getConceptStatus(2);
        assertThat(conceptStatusAccept, containsString("New"));
        final String conceptStatusExisting = reviewProposalPage.getConceptStatus(3);
        assertThat(conceptStatusExisting, containsString("Existing"));
    }
}
