Feature: Data Tables
  As a calculator user
  I want to pass tabular data to steps
  So that I can perform operations on multiple values at once

  Background:
    Given I have a calculator

  @data-tables
  Scenario: Sum a list of numbers using a data table
    Given I have the following numbers:
      | 10 |
      | 20 |
      | 30 |
      | 40 |
    When I sum them
    Then the result should be 100

  @data-tables
  Scenario: Sum another list of numbers
    Given I have the following numbers:
      | 1  |
      | 2  |
      | 3  |
      | 4  |
      | 5  |
    When I sum them
    Then the result should be 15

  @data-tables
  Scenario: Calculate average of numbers
    Given I have the following numbers:
      | 10 |
      | 20 |
      | 30 |
    When I calculate the average
    Then the result should be approximately 20.0

  @data-tables
  Scenario: Calculate average with decimal result
    Given I have the following numbers:
      | 1 |
      | 2 |
    When I calculate the average
    Then the result should be approximately 1.5
