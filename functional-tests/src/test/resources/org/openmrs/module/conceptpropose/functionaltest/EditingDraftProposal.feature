Feature: Edit Draft Proposal
  As an administrator
  I want to edit a draft proposal
  So that I can change the details

  @Selenium
  Scenario: Edit Draft Proposal
    Given I have a saved draft proposal
    When I change the details and save
    Then the proposal is stored with the new details