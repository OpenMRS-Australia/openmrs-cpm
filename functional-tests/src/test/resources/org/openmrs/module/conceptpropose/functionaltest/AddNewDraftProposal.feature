Feature: Add New Draft Proposal
  As an administrator
  I want to save a draft proposal
  So that I can review and edit it

  @Selenium
  Scenario: Save Draft Proposal
    Given I have a new proposal with all necessary details
    When I save
    Then the proposal is stored with the details