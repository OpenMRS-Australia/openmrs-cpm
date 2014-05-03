Feature: Accept or Reject Proposals
  As Dictionary Manager
  I want to be able to Accept, Reject or mark as "Already Exists" concepts within a proposal
  So that Lynette knows the status of each concept within her proposal

  @Selenium
  Scenario:
    Given that I am logged in as Dictionary Manager
    And that I have not yet reviewed a concept
    Then the concept's status should be 'Pending'

  @Selenium
  Scenario:
    Given that I am logged in as Dictionary Manager
    And that I have navigated to a not-yet-reviewed concept's review page
    When I mark a concept as accepted, rejected or "already exists"
    Then the concept's status should be updated accordingly

  Scenario:
    Given that I am logged in as Lynette
    And that a submitted proposal X is not yet reviewed
    When that I am viewing the details of submitted proposal X
    Then the concept's status should be 'Pending'

  Scenario:
    Given that I am logged in as Lynette
    And that a submitted proposal X is reviewed
    When I view the submitted proposal's details
    Then the concept's status should be shown accordingly
