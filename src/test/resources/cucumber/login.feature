Feature: Login
  Scenario: Successful login
    Given I am on the 'index' page
    When I enter the username 'test_username'
    And I enter the password 'test_password'
    And I click the 'submit' button
    Then The login is a 'success'

  Scenario: Failed login with wrong username
    Given I am on the 'index' page
    When I enter the username 'bad_username'
    And I enter the password 'test_password'
    And I click the 'submit' button
    Then The login is a 'failure'

  Scenario: Failed login with wrong password
    Given I am on the 'index' page
    When I enter the username 'test_username'
    And I enter the password 'bad_password'
    And I click the 'submit' button
    Then The login is a 'failure'

