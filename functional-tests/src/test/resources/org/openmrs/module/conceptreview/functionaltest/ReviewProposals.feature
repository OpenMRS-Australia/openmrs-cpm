Feature: Review page heading
  As Dictionary Manager
  I want the Incoming Proposals page to be labelled as Review Proposals
  So that the intention of this page is clear

  @Selenium
  Scenario:
    Given that I am logged in as Dictionary Manager
    When I am viewing the Concept Proposal module menu
    Then I see an option for Review Proposals to access incoming proposals

  @Selenium
  Scenario:
    Given that I am logged in as Dictionary Manager
    When I view the page that is currently named Incoming Proposals
    Then I see the page labelled as Review Proposals