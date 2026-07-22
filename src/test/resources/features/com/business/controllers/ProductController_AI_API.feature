Feature: ProductController API and MVC endpoint tests

  Background:
    * url baseUrl
    * configure followRedirects = false
    * def validProductId = karate.properties['validProductId'] || '<validProductId>'
    * def nonExistingProductId = karate.properties['nonExistingProductId'] || '<nonExistingProductId>'

  Scenario: POST /addingProduct redirects to admin services after adding product form data
    Given path 'addingProduct'
    And form field pname = 'Product Name'
    And form field pprice = 1200.50
    And form field pdescription = 'Product description'
    When method post
    Then status 302
    And match header Location == '#regex (/admin/services|.*/admin/services)'

  Scenario: POST /addingProduct fails when numeric product price is invalid
    Given path 'addingProduct'
    And form field pname = 'Product Name'
    And form field pprice = 'not-a-number'
    And form field pdescription = 'Product description'
    When method post
    Then status 400

  Scenario: GET /addingProduct is not allowed for POST-only endpoint
    Given path 'addingProduct'
    When method get
    Then status 405

  Scenario: GET /updatingProduct/{productId} redirects to admin services after updating product form data
    Given path 'updatingProduct', validProductId
    And param pname = 'Updated Product Name'
    And param pprice = 999.99
    And param pdescription = 'Updated product description'
    When method get
    Then status 302
    And match header Location == '#regex (/admin/services|.*/admin/services)'

  Scenario: GET /deleteProduct/{productId} redirects to admin services after deleting product
    Given path 'deleteProduct', validProductId
    When method get
    Then status 302
    And match header Location == '#regex (/admin/services|.*/admin/services)'
