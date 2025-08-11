Feature: Rijksmuseum collections

  Background:
    Given the Rijksmuseum API is available

  Scenario: Fetch details of the collection page with given parameters
    When I request the collections page 1 with page size 5 for query "Vermeer"
    Then the response status should be 200
    And the result should contain at most 5 art objects

  Scenario: Imgonly filter should return items having images
    When I request the collections page 1 with page size 10 for query "portrait" and images only
    Then the response status should be 200
    And every returned object should have an image

  Scenario: Error message should be returned if the APi key is not sent
    When I call the collections API without API key
    Then the response status should be 401
    And the response should contain error message
