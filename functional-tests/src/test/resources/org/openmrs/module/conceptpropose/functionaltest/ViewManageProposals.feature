Feature: Local Admin access to all proposals
  As a Local Admin
  I want to see all proposals in the Manage Proposals page
  So that I can navigate and view the proposals I worked with

  @Selenium
  Scenario: Local Admin opens the Manage Proposals page to find submitted proposals
    Given that I am logged in as a Local Admin
    And that at least one proposal has been submitted
    When I open the Manage Proposal page
    Then all proposals that have been submitted are displayed

  @Selenium
  Scenario: Local Admin opens the Manage Proposals page to find draft proposals
    Given that I am logged in as a Local Admin 
    And that at least one proposal has been saved as a draft
    When I open the Manage Proposal page
    Then all proposals that have been saved as a draft are displayed

  @Selenium
  Scenario: Local Admin opens the Manage Proposals page with no proposals
    Given that I am logged in as a Local Admin
    And there are no saved drafts and no submitted proposals
    When I open the Manage Proposal page
    Then there are no proposals displayed
    And a message is displayed "No proposals have been created"
