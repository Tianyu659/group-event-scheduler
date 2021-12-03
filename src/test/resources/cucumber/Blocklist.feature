Feature: Blocklist
  Scenario: Search for a user to block
    Given I am on the index page
    Then I log in with "htrojan", "asdfjkl1"
    Then I click on my name in the top right corner
    Then I enter "ttrojan" in search to block
    Then I should see "Tommy Trojan (ttrojan)" as a option to block
    Then I select "Tommy Trojan (ttrojan)" to block
    Then "Tommy Trojan (ttrojan)" should be on my list of blocked users
  Scenario: Search for an already blocked user to block
    Given I am on the index page
    Then I log in with "htrojan", "asdfjkl1"
    Then I click on my name in the top right corner
    Then I enter "ttrojan" in search to block
    Then I select "Tommy Trojan (ttrojan)" to block
    Then I enter "ttrojan" in search to block
    Then "Tommy Trojan (ttrojan)" should not be an option to block
  Scenario: Search for myself to block
    Given I am on the index page
    Then I log in with "htrojan", "asdfjkl1"
    Then I click on my name in the top right corner
    Then I enter "trojan" in search to block
    Then "Hecuba Trojan (htrojan)" should not be an option to block
	Scenario: Unblock a user
		Given I am on the index page
    Then I log in with "htrojan", "asdfjkl1"
    Then I click on my name in the top right corner
    Then I enter "ttrojan" in search to block
    Then I select "Tommy Trojan (ttrojan)" to block
    Then I click on the X besides "Tommy Trojan (ttrojan)" on my blocked list
    Then "Tommy Trojan (ttrojan)" should not be on my list of blocked users
	Scenario: Inviting a user who has blocked you to a group date
		Given I am on the index page
    Then I log in with "htrojan", "asdfjkl1"
    Then I click on my name in the top right corner
    Then I enter "ttrojan" in search to block
    Then I should see "Tommy Trojan (ttrojan)" as a option to block
    Then I select "Tommy Trojan (ttrojan)" to block
    Then I click on logout in top right corner
    Then I log in with "ttrojan", "asdfjkl1"
    Then I click the create event button
    Then I attempt to invite "hecuba"
    Then "Hecuba Trojan (htrojan)" should be a disabled invite option