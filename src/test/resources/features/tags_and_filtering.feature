Feature: Tags and Filtering
  As a QA engineer
  I want to tag scenarios for selective execution
  So that I can run specific subsets of tests

  Background:
    Given I have a calculator

  @smoke @critical
  Scenario: Critical smoke test - addition
    When I add 1 and 1
    Then the result should be 2

  @smoke @critical
  Scenario: Critical smoke test - subtraction
    When I subtract 1 from 1
    Then the result should be 0

  @regression @slow
  Scenario: Slow regression test - large numbers
    When I add 1000000 and 2000000
    Then the result should be 3000000

  @regression
  Scenario: Regression test - negative numbers
    When I add -5 and -10
    Then the result should be -15

  @wip
  Scenario: Work in progress - new feature placeholder
    When I add 0 and 0
    Then the result should be 0

  @bugfix @issue-42
  Scenario: Bug fix verification for issue 42
    When I subtract 0 from 0
    Then the result should be 0
