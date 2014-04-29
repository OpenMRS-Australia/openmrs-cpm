Feature:  As Dave,
  I want to review submitted proposals in one dedicated place,
  so that I know which proposals I need to respond to

  @Selenium
  Scenario:  Display all submitted proposals for Dave's review
    Given I am logged in as Dave
    When  I go to review submitted proposals in the "Review Proposals" page
    Then  all submitted and not accepted or rejected proposals are displayed there

  @Selenium
  Scenario:  Restricting other users from seeing "Review Proposals" page
    Given I am not logged as Dave
    When  I attempt to open "Review Proposals" page
    Then  an error is displayed  confirming denied access


  @Selenium
  Scenario:  Restricting other users from accessing submitted proposals page from the Administration tab
    Given I am not logged as Dave
    When  I access the Administration tab
    Then  the link to the "Review Proposals" page is not displayed


