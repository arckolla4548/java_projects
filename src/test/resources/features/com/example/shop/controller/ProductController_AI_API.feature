Feature: ProductController API validation

  Background:
    * url baseUrl

  Scenario: GET root should successfully serve the products view endpoint
    Given path '/'
    When method get
    Then status 200
    And assert response != null

  Scenario: POST root should be rejected because only GET is mapped
    Given path '/'
    When method post
    Then status 405
