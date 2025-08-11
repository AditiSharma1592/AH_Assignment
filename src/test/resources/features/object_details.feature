Feature: Rijksmuseum object details

  Scenario: Fetch object details for a sample object
    Given I have fetched the object number from collection api with query "Rembrandt"
    When I request details for that object
    Then the response status should be 200
    And the response should contain object with fetched object number
