Feature: AdminController API and MVC endpoint tests

  Background:
    * url baseUrl
    * configure followRedirects = false
    * def validAdminEmail = karate.properties['validAdminEmail'] || '<validAdminEmail>'
    * def validAdminPassword = karate.properties['validAdminPassword'] || '<validAdminPassword>'
    * def invalidAdminEmail = karate.properties['invalidAdminEmail'] || '<invalidAdminEmail>'
    * def invalidAdminPassword = karate.properties['invalidAdminPassword'] || '<invalidAdminPassword>'
    * def validUserEmail = karate.properties['validUserEmail'] || '<validUserEmail>'
    * def validUserPassword = karate.properties['validUserPassword'] || '<validUserPassword>'
    * def validAdminId = karate.properties['validAdminId'] || '<validAdminId>'
    * def validProductId = karate.properties['validProductId'] || '<validProductId>'
    * def validUserId = karate.properties['validUserId'] || '<validUserId>'
    * def existingProductName = karate.properties['existingProductName'] || '<existingProductName>'

  Scenario: GET /adminLogin redirects to admin services for valid admin credentials
    Given path 'adminLogin'
    And param email = validAdminEmail
    And param password = validAdminPassword
    When method get
    Then status 302
    And match header Location == '#regex (/admin/services|.*/admin/services)'

  Scenario: GET /adminLogin returns Login page for invalid admin credentials
    Given path 'adminLogin'
    And param email = invalidAdminEmail
    And param password = invalidAdminPassword
    When method get
    Then status 200
    And match response == '#present'

  Scenario: GET /userlogin returns BuyProduct page for valid user credentials
    Given path 'userlogin'
    And param userEmail = validUserEmail
    And param userPassword = validUserPassword
    When method get
    Then status 200
    And match response == '#present'

  Scenario: POST /product/search returns BuyProduct page when productName is supplied
    Given path 'product', 'search'
    And form field productName = existingProductName
    When method post
    Then status 200
    And match response == '#present'

  Scenario: GET /admin/services returns Admin page response
    Given path 'admin', 'services'
    When method get
    Then status 200
    And match response == '#present'

  Scenario: POST addingAdmin redirects to admin services after adding admin form data
    Given path 'addingAdmin'
    And form field adminName = 'Admin Name'
    And form field adminEmail = 'admin@example.com'
    And form field adminPassword = 'secret'
    And form field adminNumber = '1234567890'
    When method post
    Then status 302
    And match header Location == '#regex (/admin/services|.*/admin/services)'

  Scenario: GET /updateAdmin/{adminId} returns Update Admin page for a valid admin id
    Given path 'updateAdmin', validAdminId
    When method get
    Then status 200
    And match response == '#present'

  Scenario: GET /updatingAdmin/{id} redirects to admin services after admin update
    Given path 'updatingAdmin', validAdminId
    And param adminName = 'Updated Admin'
    When method get
    Then status 302
    And match header Location == '#regex (/admin/services|.*/admin/services)'

  Scenario: GET /deleteAdmin/{id} redirects to admin services after admin delete
    Given path 'deleteAdmin', validAdminId
    When method get
    Then status 302
    And match header Location == '#regex (/admin/services|.*/admin/services)'

  Scenario: GET /addProduct returns Add Product page response
    Given path 'addProduct'
    When method get
    Then status 200
    And match response == '#present'

  Scenario: GET /updateProduct/{productId} returns Update Product page for valid product id
    Given path 'updateProduct', validProductId
    When method get
    Then status 200
    And match response == '#present'

  Scenario: GET /addUser returns Add User page response
    Given path 'addUser'
    When method get
    Then status 200
    And match response == '#present'

  Scenario: GET /updateUser/{userId} returns Update User page for valid user id
    Given path 'updateUser', validUserId
    When method get
    Then status 200
    And match response == '#present'

  Scenario: POST /product/order returns order success page for valid order form data
    Given path 'product', 'order'
    And form field oName = 'Product Name'
    And form field oPrice = 10.0
    And form field oQuantity = 3
    When method post
    Then status 200
    And match response == '#present'

  Scenario: GET /product/back returns BuyProduct page response
    Given path 'product', 'back'
    When method get
    Then status 200
    And match response == '#present'
