Feature: GroupDate
  Scenario: Create a group date
    Given I am on the index page
    Then I log in with "ttrojan", "asdfjkl1"
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
    Then I log in with "ttrojan", "asdfjkl1"
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
    Then I log in with "ttrojan", "asdfjkl1"
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
  Scenario: Group date form validation
    Given I am on the index page
    Then I log in with "ttrojan", "asdfjkl1"
    Then I click the create event button
    Then I set the event name to "test event"
    Then I set the event description to "test description"
    Then I invite user "myself"
    Then The create event button should be disabled
    Then I remove the event name
    Then I set the search name to "sports"
    Then I set the search zipcode to 90012
    Then I set the search genre to "Sports"
    Then I set the search start date to "today"
    Then I set the search end date to "next year"
    Then I click the search button
    Then I click search result 0
    Then I remove the event description
    Then I set the event name to "test event"
    Then The create event button should be disabled
    Then I remove the invites
    Then I set the event description to "test description"
    Then The create event button should be disabled
  Scenario: Event selection
    Given I am on the index page
    Then I register 3 minions
    Then I log in with "ttrojan", "asdfjkl1"
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
    Then I invite 3 minions
    Then I click the finalize button
    Then I click logout

    Then Minion 0 logs in
    Then The minion clicks the invitation to "test event"
    Then The minion is available for event 0 with interest 1
    Then The minion accepts invitation
    Then The minion logs out

    Then I log in with "ttrojan", "asdfjkl1"
    Then I click the event "test event"
    Then Event 0 should be highlighted with availability 1 and interest 1
    Then I click logout

    Then Minion 1 logs in
    Then The minion clicks the invitation to "test event"
    Then The minion is available for event 0 with interest 1
    Then The minion is available for event 1 with interest 2
    Then The minion accepts invitation
    Then The minion logs out

    Then I log in with "ttrojan", "asdfjkl1"
    Then I click the event "test event"
    Then Event 0 should be highlighted with availability 2 and interest 1
    Then I click logout

    Then Minion 2 logs in
    Then The minion clicks the invitation to "test event"
    Then The minion is available for event 1 with interest 1
    Then The minion accepts invitation
    Then The minion logs out

    Then I log in with "ttrojan", "asdfjkl1"
    Then I click the event "test event"
    Then I should see 3 responses
    Then Event 1 should be highlighted with availability 2 and interest 1

    Then I delete the invitation to minion 2
    Then I should see 2 invitees
    Then Event 0 should be highlighted with availability 2 and interest 1

    Then I delete event 0
    Then I should see 1 events
    # Event 1 becomes new event 0
    Then Event 0 should be highlighted with availability 1 and interest 1
    Then I click logout

    Then Minion 2 logs in
    Then The minion should see 0 invites

  Scenario: Group date deletion
    Given I am on the index page
    Then I log in with "ttrojan", "asdfjkl1"
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
    Then I invite user "myself"
    Then I click the finalize button
    Then I cancel deleting event 0
    Then I should see 1 events
    Then I cancel deleting invite 0
    Then I should see 1 invitees
    Then I delete event 0
    Then I should see 0 group dates
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
    Then I invite user "myself"
    Then I click the finalize button
    Then I delete invite 0
    Then I should see 0 group dates