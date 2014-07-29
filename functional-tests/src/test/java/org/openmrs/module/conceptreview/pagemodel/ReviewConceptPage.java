package org.openmrs.module.conceptreview.pagemodel;

import org.openmrs.module.conceptpropose.pagemodel.BaseCpmPage;
import org.openqa.selenium.WebDriver;

public class ReviewConceptPage extends BaseCpmPage {

    public ReviewConceptPage(WebDriver driver) {
        super(driver);
    }

    public ReviewProposalPage acceptConcept(){
        getElementByAttribute("button","ng-click", "conceptCreated()").click();
        return new ReviewProposalPage(driver);
    }
    public ReviewProposalPage rejectConcept(){
        getElementByAttribute("button","ng-click", "conceptRejected()").click();
        return new ReviewProposalPage(driver);
    }
    public ReviewProposalPage markConceptAsExisted(){
        getElementByAttribute("button","ng-click", "conceptExists()").click();
        return new ReviewProposalPage(driver);
    }
}
