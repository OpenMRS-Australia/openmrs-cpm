Feature: Concept Discussion
  As a Local Admin or Dictionary Manager
  I want to leave comments on a Concept Proposal
  so we can discuss the overall Proposal.

#  Scenario: Opening a pending concept proposal
#    Given that I am logged in as a Local Admin or Dictionary Manager
#    When I open a pending concept proposal's page
#    Then all comments are displayed in the order that they were made.
  @Selenium
  Scenario: Submitting a comment on a pending proposed concept
    Given that I am logged in as Dictionary Manager
    And that a proposal has just been submitted
    And I open the Review Proposals page
    And I click on a proposal to be reviewed
    When I click on a concept to be reviewed
    And I submit a comment
    Then my comment is displayed
#    And a timestamp is displayed And my user name is displayed with the comment