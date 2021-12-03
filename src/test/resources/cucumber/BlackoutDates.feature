Feature: Unavailable Dates
  Scenario: Add unavailable date (1 day)
  	Given I am on the index page
    Then I log in with "ttrojan", "asdfjkl1"
    Then I click on my name in the top right corner
    Then I select "01012022" to "01012022" as unavailable
    Then I click Create (unavailable date)
    Then "January 1 - January 1" should be on my list of unavailable dates
  Scenario: Add unavailable date (>1 day)
  	Given I am on the index page
    Then I log in with "ttrojan", "asdfjkl1"
    Then I click on my name in the top right corner
    Then I select "01012022" to "01022022" as unavailable
    Then I click Create (unavailable date)
    Then "January 1 - January 2" should be on my list of unavailable dates
  Scenario: Attempt to add illegal date range
  	Given I am on the index page
    Then I log in with "ttrojan", "asdfjkl1"
    Then I click on my name in the top right corner
    Then I select "01022022" to "01012022" as unavailable
    Then the Create (unavailable date) button should be disabled
  Scenario: Remove unavailable dates
  	Given I am on the index page
    Then I log in with "ttrojan", "asdfjkl1"
    Then I click on my name in the top right corner
    Then I select "01012022" to "01022022" as unavailable
    Then I click Create (unavailable date)
    Then "January 1 - January 2" should be on my list of unavailable dates
    Then I delete "January 1 - January 2" from my list of unavailable dates
    Then "January 1 - January 2" should not be on my list of unavailable dates
#	Scenario: Inviting a user unavailable during the proposed date range
#		Given I am on the index page
    #Then I log in with "ttrojan", "asdfjkl1"
    #Then I click on my name in the top right corner
    #Then I select "01012022" to "01312022" as unavailable
    #Then I click Create (unavailable date)
    #Then "January 1 - January 31" should be on my list of unavailable dates
    #Then I click on logout in top right corner
    #Then I log in with "htrojan", "asdfjkl1"
    #Then I click the create event button
    #Then I set the search start date to "01022022"
    #Then I set the search end date to "01032022"
    #Then I attempt to invite "tommy"
    #Then "Tommy Trojan (ttrojan)" should be a disabled invite option
    