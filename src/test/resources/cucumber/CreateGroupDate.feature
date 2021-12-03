Feature: Create a Group Date Event
  Scenario: Open the 'Create' page
    Given I have logged in and am on my home page
    Then I click + Icon next to "My Group Dates on Home Page"
  Scenario: Name and describe your event
    Given I am on the create page
    When I click on the box titled 'Group date name'
    And I enter the name of my event
    Then I should see the name of my event displayed in the aforementioned box
    When I click on the box titled 'Group date description'
    And I enter the description of my event
    Then I should see the description of my event displayed in the 'Group date description' box
  Scenario: 
