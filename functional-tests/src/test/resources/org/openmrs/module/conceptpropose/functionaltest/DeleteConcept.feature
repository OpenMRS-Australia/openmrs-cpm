Feature: Delete Concept
  As an administrator
  I want to delete concept from a draft proposal
  So that the concept is no longer there

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
  Scenario: Start to Delete Concept and Cancel
    Given I have a saved draft proposal with at least 1 concept
    When I start to delete a concept then cancel
    Then the concept still exists in the proposal

  @Selenium
  Scenario: Delete Concept
    Given I have a saved draft proposal with at least 1 concept
    When I delete a concept
    Then the concept is deleted