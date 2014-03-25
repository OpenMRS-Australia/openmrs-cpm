Feature: Delete Proposal
  As an administrator
  I want to delete a draft proposal
  So that the proposal is no longer in my list

  @Selenium
  Scenario: Deletes draft proposal
    Given I have a saved draft proposal
    When I click the Delete Proposal button
    And I confirm I want to delete
    Then the proposal is not on the Monitor Proposals page