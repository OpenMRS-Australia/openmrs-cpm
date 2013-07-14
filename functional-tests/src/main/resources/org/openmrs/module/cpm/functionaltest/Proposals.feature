Feature: Proposal Page
  As an administrator
  I want to create a proposal
  So that I can propose new concepts

  Scenario: Save draft proposal
    Given I'm on the Create Proposal page
    When I enter stuff, and hit save
    Then an Alert box appears that says 'Saved!'
    
  Scenario: View draft proposal
  
  Scenario: Submit proposal
  
  Scenario: View submitted proposal