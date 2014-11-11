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
  Scenario: Add Concept to Draft Proposal
    Given I have a saved draft proposal with zero concepts
    When I add a concept and save
    Then the proposal is stored with the added concept details

  @Selenium
  Scenario: Submit Draft Proposal
    Given I have a saved draft proposal with at least 1 concept
    When I submit the proposal
    Then the proposal is sent to the dictionary manager

