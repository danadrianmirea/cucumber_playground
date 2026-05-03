Feature: Background Demo
  As a calculator user
  I want to see how Background works with multiple scenarios
  So that I understand shared setup steps

  Background:
    Given I have a calculator
    And I have turned on the calculator

  @background
  Scenario: Addition after turning on
    When I add 3 and 7
    Then the result should be 10

  @background
  Scenario: Subtraction after turning on
    When I subtract 4 from 9
    Then the result should be 5

  @background
  Scenario: Multiplication after turning on
    When I multiply 6 and 7
    Then the result should be 42
