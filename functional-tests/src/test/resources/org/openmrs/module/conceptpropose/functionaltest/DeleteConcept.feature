Feature: Delete Concept
  As an administrator
  I want to delete concept from a draft proposal
  So that the concept is no longer there

  @Selenium
  Scenario: Delete Concept
    Given I have a saved draft proposal with at least 1 concept
    When I delete a concept
    Then the concept is deleted