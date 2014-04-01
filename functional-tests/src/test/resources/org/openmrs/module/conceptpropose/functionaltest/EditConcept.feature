Feature: Edit Concept
  As an administrator
  I want to edit a concept comment
  So that I can change the details

  @Selenium
  Scenario: Save Draft Proposal
    Given I have a new proposal with all necessary details
    When I save
    Then the proposal is stored with the details

  @Selenium
  Scenario: Add Concept to Draft Proposal
    Given I have a saved draft proposal
    When I add a concept and save
    Then the proposal is stored with the added concept details

  @Selenium
  Scenario: Edit Concept
    Given I have a saved draft proposal with at least 1 concept
    When I change the first concept comment
    Then the concept comment is saved