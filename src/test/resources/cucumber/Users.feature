Feature: User Login and Registration
  Scenario: Open hello page from index page
    Given I am on the index page
    Then I should see header "Login"
  Scenario: Register from index page
    Given I am on the index page
    Then I click on login in top right corner
    Then I click on register
    Then I enter registration info: "noahbkim", "asdfjkl1", "asdfjkl1", "Noah", "Kim"
    Then I click register
    Then I should see header "Login"
  Scenario: Register with duplicate info
    Given I am on the index page
    Then I click on login in top right corner
    Then I click on register
    Then I enter registration info: "ttrojan", "asdfjkl1", "asdfjkl1", "Tommy", "Trojan"
    Then I click register
    Then I should see register page warning "username is already taken"
  Scenario: Log in from index page
    Given I am on the index page
    Then I click on login in top right corner
    Then I log in with "ttrojan", "asdfjkl1"
    Then I should see header "Hi, Tommy"
  Scenario: Log in and out from index page
    Given I am on the index page
    Then I click on login in top right corner
    Then I log in with "ttrojan", "asdfjkl1"
    Then I should see header "Hi, Tommy"
    Then I click logout
    Then I should see header "Login"
  Scenario: Log in with incorrect credentials
    Given I am on the index page
    Then I click on login in top right corner
    Then I log in with "ttrojan", "oops"
    Then I should see login error
  Scenario: Example login with fixture user, delete this eventually
    Given I am on the index page
    Then I log in with "ttrojan", "asdfjkl1"
    Then I should see header "Hi, Tommy"
  Scenario: Register invalid information
    Given I am on the index page
    Then I click on login in top right corner
    Then I click on register
    Then I enter registration info: "noahbkim", "asdfjkl1", "asdfjkl1", "Noah", ""
    Then The create user button should be disabled
    Then I reset the registration form
    Then I enter registration info: "noahbkim", "asdfjkl1", "asdfjkl1", "", "Kim"
    Then The create user button should be disabled
    Then I reset the registration form
    Then I enter registration info: "noahbkim", "asdfjkl;", "asdfjkl;", "Noah", "Kim"
    Then The create user button should be disabled
    Then I reset the registration form
    Then I enter registration info: "noahbkim", "asdfjkl1", "asdfjkl2", "Noah", "Kim"
    Then The create user button should be disabled
    Then I reset the registration form
    Then I enter registration info: "", "asdfjkl1", "asdfjkl1", "Noah", "Kim"
    Then The create user button should be disabled