Feature: Basic Arithmetic Operations
  As a calculator user
  I want to perform basic arithmetic operations
  So that I can compute mathematical results

  Background:
    Given I have a calculator

  @smoke
  Scenario: Add two positive numbers
    When I add 2 and 3
    Then the result should be 5

  @smoke
  Scenario: Subtract two numbers
    When I subtract 5 from 10
    Then the result should be 5

  @smoke
  Scenario: Multiply two numbers
    When I multiply 4 and 3
    Then the result should be 12

  @smoke
  Scenario: Divide two numbers
    When I divide 10 by 2
    Then the result should be 5

  @regression
  Scenario: Divide by zero
    When I divide 10 by 0
    Then an arithmetic error should be thrown with message "Division by zero is not allowed"

  @regression
  Scenario: Power of a number
    When I raise 2 to the power of 3
    Then the result should be 8

  @regression
  Scenario: Square root of a positive number
    When I calculate the square root of 9
    Then the result should be approximately 3.0

  @regression
  Scenario: Square root of a negative number
    When I calculate the square root of -1
    Then an illegal argument error should be thrown with message "Cannot calculate square root of a negative number"
