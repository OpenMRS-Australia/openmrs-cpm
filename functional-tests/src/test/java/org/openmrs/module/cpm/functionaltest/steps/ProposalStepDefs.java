package org.openmrs.module.cpm.functionaltest.steps;

import cucumber.api.java.en.Given;
import cucumber.runtime.PendingException;
import org.openmrs.module.cpm.pagemodel.AdminPage;
import org.openmrs.module.cpm.pagemodel.CreateProposalPage;
import org.openmrs.module.cpm.pagemodel.SettingsPage;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ProposalStepDefs {

    private AdminPage adminPage;

    @Given("^I have a new proposal with all necessary details$")
    public void I_have_a_new_proposal_with_all_necessary_details() throws Throwable {
        CreateProposalPage createProposalPage = adminPage.navigateToCreateProposalPage();
        createProposalPage.enterDetails();
    }
}
