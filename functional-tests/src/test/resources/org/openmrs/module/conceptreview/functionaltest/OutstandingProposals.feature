Feature: View submitted proposals in one place
  As Dictionary Manager
  I want to view submitted proposals in one dedicated place
  So that I know which proposals I need to respond to.

  @Selenium
  Scenario:
    Given that I am logged in as Lynette
    When I go to review submitted proposals in the "Review Proposals" page
    Then an error is displayed confirming denied access

  @Selenium
  Scenario:
    Given that I am logged in as Lynette
    When I am viewing the Concept Proposal module menu
    Then I do not see an option for Review Proposals to access incoming proposals

  @Selenium
  Scenario:
    Given that I am logged in as Dictionary Manager
    And I note how many proposals there are now
    When I go to review submitted proposals in the "Review Proposals" page
    Then no proposals are displayed there

  @Selenium
  Scenario:
    Given that I am logged in as Dictionary Manager
    And I note how many proposals there are now
    And a proposal gets created
    When I go to review submitted proposals in the "Review Proposals" page
    Then there is one extra proposal displayed there

  @Selenium
  Scenario:
    Given that I am logged in as Dictionary Manager
    And I note how many proposals there are now
    And a proposal gets created
    And I reject that proposal 
    When I go to review submitted proposals in the "Review Proposals" page
    Then there are no extra proposals displayed there

  @Selenium
  Scenario:
    Given that I am logged in as Dictionary Manager
    And I note how many proposals there are now
    And a proposal gets created
    And I accept that proposal 
    When I go to review submitted proposals in the "Review Proposals" page
    Then there are no extra proposals displayed there

  @Selenium
  Scenario:
    Given that I am logged in as Dictionary Manager
    And I note how many proposals there are now
    And a proposal gets created
    And I mark that proposal as "Already Exists"
    When I go to review submitted proposals in the "Review Proposals" page
    Then there are no extra proposals displayed there

