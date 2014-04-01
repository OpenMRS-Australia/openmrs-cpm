Feature: Delete Draft Proposal
  As an administrator
  I want to delete draft proposal
  So that the proposal is no longer there

  @Selenium
  Scenario: Delete Draft Proposal
    Given I have a saved draft proposal
    When I delete the proposal
    Then the proposal is deleted