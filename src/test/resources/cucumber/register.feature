Feature: Register
  Scenario: Successfully register - redirect to calendar page
    Given I am on the 'register' page
    When I enter the first name 'first name'
    And I enter the last name 'last name'
    And I enter the valid email 'email@usc.edu'
    And I enter the r_password 'some password'
    And I click the 'signup' button
    Then I am redirected to the 'calendar' page

  Scenario: Failed register - stay on page
    Given I am on the 'register' page
    When I enter the first name 'first name'
    And I enter the last name 'last name'
    And I click the 'signup' button
    Then I should see header 'Sign Up'

  Scenario: Cancel register - redirect to login page
      Given I am on the 'register' page
      When I enter the first name 'first name'
      And I enter the last name 'last name'
      And I click the 'cancel' button
      Then I should see header 'Log In'


