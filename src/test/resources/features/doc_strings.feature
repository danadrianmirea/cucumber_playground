Feature: Doc Strings
  As a calculator user
  I want to pass multi-line text to steps
  So that I can handle complex input data

  @doc-strings
  Scenario: Parse a multi-line expression
    Given I have a calculation expression:
      """
      add
      2
      3
      """
    When I evaluate the expression
    Then the result should be 5

  @doc-strings
  Scenario: Parse a multi-line expression with subtraction
    Given I have a calculation expression:
      """
      subtract
      10
      3
      """
    When I evaluate the expression
    Then the result should be 7

  @doc-strings
  Scenario: Parse a multi-line expression with multiplication
    Given I have a calculation expression:
      """
      multiply
      4
      5
      """
    When I evaluate the expression
    Then the result should be 20

  @doc-strings
  Scenario: Parse a multi-line expression with division
    Given I have a calculation expression:
      """
      divide
      20
      4
      """
    When I evaluate the expression
    Then the result should be 5
