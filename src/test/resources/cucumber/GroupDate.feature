Feature: GroupDate
  Scenario: Create a group date
    Given I am on the index page
    Then I log in with "ttrojan", "asdfjkl;"
    Then I click the create event button
    Then I set the event name to "test event"
    Then I set the event description to "test description"
    Then I set the search name to "sports"
    Then I set the search zipcode to 90012
    Then I set the search genre to "Sports"
    Then I set the search start date to "today"
    Then I set the search end date to "next year"
    Then I click the search button
    Then I click search result 0
    Then I click search result 1
    Then I invite user "myself"
    Then I click the finalize button
    Then I should see the event name "test event"
    Then I should see the event description "test description"
    Then I should see 2 events
    Then I should see 1 invitees
    Then I click the logo in the top left
    Then I should see date with name "test event"
  Scenario: See Invitation
    Given I am on the index page
    Then I log in with "ttrojan", "asdfjkl;"
    Then I click the create event button
    Then I set the event name to "test event"
    Then I set the event description to "test description"
    Then I set the search name to "sports"
    Then I set the search zipcode to 90012
    Then I set the search genre to "Sports"
    Then I set the search start date to "today"
    Then I set the search end date to "next year"
    Then I click the search button
    Then I click search result 0
    Then I click search result 1
    Then I invite user "myself"
    Then I click the finalize button
    Then I click the logo in the top left
    Then I should see an invitation to the date "test event"
    Then I click the invitation to the group date "test event"
    Then I should see information for the group date "test event"
  Scenario: Decline Invitation
    Given I am on the index page
    Then I log in with "ttrojan", "asdfjkl;"
    Then I click the create event button
    Then I set the event name to "test event"
    Then I set the event description to "test description"
    Then I set the search name to "sports"
    Then I set the search zipcode to 90012
    Then I set the search genre to "Sports"
    Then I set the search start date to "today"
    Then I set the search end date to "next year"
    Then I click the search button
    Then I click search result 0
    Then I click search result 1
    Then I invite user "myself"
    Then I click the finalize button
    Then I click the logo in the top left
    Then I click the invitation to the group date "test event"
    Then I decline the group date
    Then I should see 0 invites
