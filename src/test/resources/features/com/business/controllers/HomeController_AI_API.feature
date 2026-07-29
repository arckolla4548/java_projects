Feature: HomeController API and MVC endpoint tests

  Background:
    * url baseUrl
    * configure followRedirects = false

  Scenario: GET /test returns application health message
    Given path 'test'
    When method get
    Then status 200
    And match response == 'Application is working!'

  Scenario: POST /test is not allowed for GET-only endpoint
    Given path 'test'
    When method post
    Then status 405

  Scenario: GET /home returns the Home page response
    Given path 'home'
    When method get
    Then status 200
    And match response == '#present'

  Scenario: GET /products returns the Products page response
    Given path 'products'
    When method get
    Then status 200
    And match response == '#present'

  Scenario: GET /location returns the Locate Us page response
    Given path 'location'
    When method get
    Then status 200
    And match response == '#present'

  Scenario: GET /about returns the About page response
    Given path 'about'
    When method get
    Then status 200
    And match response == '#present'

  Scenario: GET /login returns the Login page response
    Given path 'login'
    When method get
    Then status 200
    And match response == '#present'
