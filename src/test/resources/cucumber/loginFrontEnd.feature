Feature: Login FrontEnd
  Scenario: Create Account - redirect to register page
    Given I am on the 'index' page
    When I enter the username 'test_username'
    And I enter the password 'test_password'
    And I click the link 'Create Account'
    Then I am redirected to the 'register' page

  Scenario: Successful login - redirect to calendar page
    Given I am on the 'index' page
    When I enter the username 'test_username'
    And I enter the password 'test_password'
    And I click the 'submit' button
    Then I am redirected to the 'calendar' page

  Scenario: Failed login - stay on page
    Given I am on the 'index' page
    When I enter the username 'bad_username'
    And I enter the password 'test_password'
    And I click the 'submit' button
    Then I should see header 'Log In'