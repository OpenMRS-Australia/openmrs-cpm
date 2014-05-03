Feature: Delete Draft Proposal
  As an administrator
  I want to delete draft proposal
  So that the proposal is no longer there

  @Selenium
  Scenario: Save Draft Proposal
    Given I have a new proposal with all necessary details
    When I save
    Then the proposal is stored with the details

  @Selenium
  Scenario: Start to Delete Draft Proposal and Cancel
    Given I have a saved draft proposal
    When I change the details and save
    And I have a saved draft proposal for deletion
    And I start to delete the proposal then cancel
    Then the proposal still exists

  @Selenium
  Scenario: Delete Draft Proposal
    Given I have a saved draft proposal
    When I change the details and save
    And I have a saved draft proposal for deletion
    And I delete the proposal
    Then the proposal is deleted

