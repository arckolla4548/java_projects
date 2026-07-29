Feature: UserController API and MVC endpoint tests

  Background:
    * url baseUrl
    * configure followRedirects = false
    * def validUserId = karate.properties['validUserId'] || '<validUserId>'

  Scenario: POST /addingUser redirects to admin services after adding user form data
    Given path 'addingUser'
    And form field uname = 'User Name'
    And form field uemail = 'user@example.com'
    And form field upassword = 'secret'
    And form field unumber = 9876543210
    When method post
    Then status 302
    And match header Location == '#regex (/admin/services|.*/admin/services)'

  Scenario: POST /addingUser fails when numeric user number is invalid
    Given path 'addingUser'
    And form field uname = 'User Name'
    And form field uemail = 'user@example.com'
    And form field upassword = 'secret'
    And form field unumber = 'not-a-number'
    When method post
    Then status 400

  Scenario: GET /addingUser is not allowed for POST-only endpoint
    Given path 'addingUser'
    When method get
    Then status 405

  Scenario: GET /updatingUser/{id} redirects to admin services after updating user form data
    Given path 'updatingUser', validUserId
    And param uname = 'Updated User Name'
    And param uemail = 'updated-user@example.com'
    And param upassword = 'secret'
    And param unumber = 9876543210
    When method get
    Then status 302
    And match header Location == '#regex (/admin/services|.*/admin/services)'

  Scenario: GET /deleteUser/{id} redirects to admin services after deleting user
    Given path 'deleteUser', validUserId
    When method get
    Then status 302
    And match header Location == '#regex (/admin/services|.*/admin/services)'
