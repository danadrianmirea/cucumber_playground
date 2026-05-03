Feature: Scenario Outline and Examples
  As a calculator user
  I want to test multiple data sets with the same scenario
  So that I can verify behavior across different inputs

  Background:
    Given I have a calculator

  @data-driven
  Scenario Outline: Add multiple pairs of numbers
    When I add <a> and <b>
    Then the result should be <expected>

    Examples:
      | a  | b  | expected |
      | 0  | 0  | 0        |
      | 1  | 1  | 2        |
      | -1 | 5  | 4        |
      | 100| 200| 300      |
      | -5 | -3 | -8       |

  @data-driven
  Scenario Outline: Subtract multiple pairs of numbers
    When I subtract <b> from <a>
    Then the result should be <expected>

    Examples:
      | a  | b  | expected |
      | 10 | 3  | 7        |
      | 5  | 5  | 0        |
      | 3  | 10 | -7       |
      | 0  | 7  | -7       |

  @data-driven
  Scenario Outline: Multiply multiple pairs of numbers
    When I multiply <a> and <b>
    Then the result should be <expected>

    Examples:
      | a  | b  | expected |
      | 0  | 5  | 0        |
      | 3  | 4  | 12       |
      | -2 | 6  | -12      |
      | -3 | -4 | 12       |

  @data-driven
  Scenario Outline: Divide multiple pairs of numbers
    When I divide <a> by <b>
    Then the result should be <expected>

    Examples:
      | a  | b  | expected |
      | 10 | 2  | 5        |
      | 9  | 3  | 3        |
      | 7  | 2  | 3        |
      | -8 | 4  | -2       |
