Feature: Submit Draft Proposal
  As an administrator
  I want to submit a draft proposal
  So that the dictionary manager receives the proposal

  @Selenium
  Scenario: Save Draft Proposal
    Given I have a new proposal with all necessary details
    When I save
    Then the proposal is stored with the details

  @Selenium
  Scenario: Submit Draft Proposal
    Given I have a saved draft proposal
    When I submit the proposal
    Then the proposal is sent to the dictionary manager

