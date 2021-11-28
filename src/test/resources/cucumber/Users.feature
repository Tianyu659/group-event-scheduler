Feature: Frontend
  Scenario: Open hello page from index page
    Given I am on the index page
    Then I should see header "Welcome to Groupie!"
  Scenario: Register from index page
    Given I am on the index page
    Then I click on login in top right corner
    Then I click on register
    Then I enter registration info: "noahbkim", "asdfjkl;", "asdfjkl;", "Noah", "Kim"
    Then I click register
    Then I should see header "Login"
	Scenario: Register with duplicate info
		Given I am on the index page
    Then I click on login in top right corner
    Then I click on register
    Then I enter registration info: "noahbkim", "asdfjkl;", "asdfjkl;", "Noah", "Kim"
    Then I click register
    Then I should see register page warning "username is already taken"
  Scenario: Log in from index page
    Given I am on the index page
    Then I click on login in top right corner
    Then I log in with "noahbkim", "asdfjkl;"
    Then I click login
    Then I should see header "Hi, Noah"
  Scenario: Log in and out from index page
    Given I am on the index page
    Then I click on login in top right corner
    Then I log in with "noahbkim", "asdfjkl;"
    Then I click login
    Then I should see header "Hi, Noah"
    Then I click logout
    Then I should see header "Login"
  Scenario: Log in with incorrect credentials
    Given I am on the index page
    Then I click on login in top right corner
    Then I log in with "noahbkim", "oops"
    Then I click login
    Then I should see login error
  Scenario: Example login with fixture user, delete this eventually
    Given I am on the index page
    Then I log in with "ttrojan", "asdfjkl;"
    Then I should see header "Hi, Tommy"