Feature: Review page heading
  As Dictionary Manager
  I want the Incoming Proposals page to be labelled as Review Proposals
  So that the intention of this page is clear

  @Selenium
  Scenario:
    Given that I am logged in as Dictionary Manager
    When I am viewing the Admin page
    Then I see an option for Review Proposals to access incoming proposals

#  This functionality tests page labelling and is not resistant to localisation (the page label is
#  localisation-dependent). Furthermore, the functionality associated with this scenario is tested in
#  AcceptOrRejectProposals.feature.
#  @Selenium
#  Scenario:
#    Given that I am logged in as Dictionary Manager
#    When I view the page that is currently named Incoming Proposals
#    Then I see the page labelled as Review Proposals