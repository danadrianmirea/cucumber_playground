package com.example.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit component tests for the Calculator class.
 * These tests complement the Cucumber BDD tests by providing
 * direct unit-level verification of each operation.
 */
@DisplayName("Calculator Component Tests")
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    // ============================================================
    // Addition Tests
    // ============================================================

    @Nested
    @DisplayName("Addition")
    class AdditionTests {

        @Test
        @DisplayName("should add two positive numbers")
        void addTwoPositiveNumbers() {
            assertEquals(5, calculator.add(2, 3));
        }

        @Test
        @DisplayName("should add positive and negative numbers")
        void addPositiveAndNegative() {
            assertEquals(-2, calculator.add(3, -5));
        }

        @Test
        @DisplayName("should add two negative numbers")
        void addTwoNegativeNumbers() {
            assertEquals(-8, calculator.add(-3, -5));
        }

        @Test
        @DisplayName("should add zero")
        void addZero() {
            assertEquals(7, calculator.add(7, 0));
            assertEquals(7, calculator.add(0, 7));
        }

        @ParameterizedTest(name = "{0} + {1} = {2}")
        @CsvSource({
                "0, 0, 0",
                "1, 1, 2",
                "-1, 5, 4",
                "100, 200, 300",
                "-5, -3, -8",
                "999999, 1, 1000000"
        })
        @DisplayName("should add multiple pairs of numbers")
        void addMultiplePairs(int a, int b, int expected) {
            assertEquals(expected, calculator.add(a, b));
        }
    }

    // ============================================================
    // Subtraction Tests
    // ============================================================

    @Nested
    @DisplayName("Subtraction")
    class SubtractionTests {

        @Test
        @DisplayName("should subtract smaller from larger")
        void subtractSmallerFromLarger() {
            assertEquals(5, calculator.subtract(10, 5));
        }

        @Test
        @DisplayName("should subtract larger from smaller (negative result)")
        void subtractLargerFromSmaller() {
            assertEquals(-5, calculator.subtract(5, 10));
        }

        @Test
        @DisplayName("should subtract equal numbers (result zero)")
        void subtractEqualNumbers() {
            assertEquals(0, calculator.subtract(7, 7));
        }

        @Test
        @DisplayName("should subtract zero")
        void subtractZero() {
            assertEquals(10, calculator.subtract(10, 0));
        }

        @Test
        @DisplayName("should subtract from zero (negative result)")
        void subtractFromZero() {
            assertEquals(-7, calculator.subtract(0, 7));
        }

        @ParameterizedTest(name = "{0} - {1} = {2}")
        @CsvSource({
                "10, 3, 7",
                "5, 5, 0",
                "3, 10, -7",
                "0, 7, -7",
                "-5, -3, -2",
                "-5, 3, -8"
        })
        @DisplayName("should subtract multiple pairs of numbers")
        void subtractMultiplePairs(int a, int b, int expected) {
            assertEquals(expected, calculator.subtract(a, b));
        }
    }

    // ============================================================
    // Multiplication Tests
    // ============================================================

    @Nested
    @DisplayName("Multiplication")
    class MultiplicationTests {

        @Test
        @DisplayName("should multiply two positive numbers")
        void multiplyTwoPositiveNumbers() {
            assertEquals(12, calculator.multiply(3, 4));
        }

        @Test
        @DisplayName("should multiply by zero")
        void multiplyByZero() {
            assertEquals(0, calculator.multiply(5, 0));
            assertEquals(0, calculator.multiply(0, 5));
        }

        @Test
        @DisplayName("should multiply positive and negative")
        void multiplyPositiveAndNegative() {
            assertEquals(-12, calculator.multiply(3, -4));
        }

        @Test
        @DisplayName("should multiply two negative numbers")
        void multiplyTwoNegativeNumbers() {
            assertEquals(12, calculator.multiply(-3, -4));
        }

        @ParameterizedTest(name = "{0} * {1} = {2}")
        @CsvSource({
                "0, 5, 0",
                "3, 4, 12",
                "-2, 6, -12",
                "-3, -4, 12",
                "1, 100, 100",
                "1000, 1000, 1000000"
        })
        @DisplayName("should multiply multiple pairs of numbers")
        void multiplyMultiplePairs(int a, int b, int expected) {
            assertEquals(expected, calculator.multiply(a, b));
        }
    }

    // ============================================================
    // Division Tests
    // ============================================================

    @Nested
    @DisplayName("Division")
    class DivisionTests {

        @Test
        @DisplayName("should divide evenly")
        void divideEvenly() {
            assertEquals(5, calculator.divide(10, 2));
        }

        @Test
        @DisplayName("should divide with truncation")
        void divideWithTruncation() {
            assertEquals(3, calculator.divide(7, 2));
        }

        @Test
        @DisplayName("should divide negative numbers")
        void divideNegativeNumbers() {
            assertEquals(-2, calculator.divide(-8, 4));
            assertEquals(-2, calculator.divide(8, -4));
            assertEquals(2, calculator.divide(-8, -4));
        }

        @Test
        @DisplayName("should divide by one")
        void divideByOne() {
            assertEquals(42, calculator.divide(42, 1));
        }

        @Test
        @DisplayName("should throw ArithmeticException when dividing by zero")
        void divideByZero() {
            ArithmeticException exception = assertThrows(
                    ArithmeticException.class,
                    () -> calculator.divide(10, 0)
            );
            assertEquals("Division by zero is not allowed", exception.getMessage());
        }

        @ParameterizedTest(name = "{0} / {1} = {2}")
        @CsvSource({
                "10, 2, 5",
                "9, 3, 3",
                "7, 2, 3",
                "-8, 4, -2",
                "0, 5, 0"
        })
        @DisplayName("should divide multiple pairs of numbers")
        void divideMultiplePairs(int a, int b, int expected) {
            assertEquals(expected, calculator.divide(a, b));
        }
    }

    // ============================================================
    // Power Tests
    // ============================================================

    @Nested
    @DisplayName("Power")
    class PowerTests {

        @Test
        @DisplayName("should calculate power of a number")
        void calculatePower() {
            assertEquals(8, calculator.power(2, 3));
        }

        @Test
        @DisplayName("should return 1 for exponent 0")
        void powerOfZero() {
            assertEquals(1, calculator.power(5, 0));
        }

        @Test
        @DisplayName("should return base for exponent 1")
        void powerOfOne() {
            assertEquals(7, calculator.power(7, 1));
        }

        @Test
        @DisplayName("should calculate power of zero")
        void powerOfZeroBase() {
            assertEquals(0, calculator.power(0, 5));
        }

        @ParameterizedTest(name = "{0} ^ {1} = {2}")
        @CsvSource({
                "2, 3, 8",
                "3, 2, 9",
                "5, 0, 1",
                "10, 1, 10",
                "0, 5, 0",
                "2, 10, 1024"
        })
        @DisplayName("should calculate power for multiple inputs")
        void powerMultipleInputs(int base, int exponent, int expected) {
            assertEquals(expected, calculator.power(base, exponent));
        }
    }

    // ============================================================
    // Square Root Tests
    // ============================================================

    @Nested
    @DisplayName("Square Root")
    class SquareRootTests {

        @Test
        @DisplayName("should calculate square root of a perfect square")
        void sqrtOfPerfectSquare() {
            assertEquals(3.0, calculator.sqrt(9), 0.001);
        }

        @Test
        @DisplayName("should calculate square root of zero")
        void sqrtOfZero() {
            assertEquals(0.0, calculator.sqrt(0), 0.001);
        }

        @Test
        @DisplayName("should calculate square root of one")
        void sqrtOfOne() {
            assertEquals(1.0, calculator.sqrt(1), 0.001);
        }

        @Test
        @DisplayName("should calculate square root of a non-perfect square")
        void sqrtOfNonPerfectSquare() {
            assertEquals(1.414, calculator.sqrt(2), 0.001);
        }

        @Test
        @DisplayName("should throw IllegalArgumentException for negative input")
        void sqrtOfNegative() {
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> calculator.sqrt(-1)
            );
            assertEquals("Cannot calculate square root of a negative number", exception.getMessage());
        }

        @ParameterizedTest(name = "sqrt({0}) = {1}")
        @CsvSource({
                "0, 0.0",
                "1, 1.0",
                "4, 2.0",
                "9, 3.0",
                "16, 4.0",
                "100, 10.0"
        })
        @DisplayName("should calculate square root for multiple inputs")
        void sqrtMultipleInputs(int input, double expected) {
            assertEquals(expected, calculator.sqrt(input), 0.001);
        }
    }

    // ============================================================
    // Sum Tests
    // ============================================================

    @Nested
    @DisplayName("Sum")
    class SumTests {

        @Test
        @DisplayName("should sum a list of numbers")
        void sumListOfNumbers() {
            List<Integer> numbers = Arrays.asList(10, 20, 30, 40);
            assertEquals(100, calculator.sum(numbers));
        }

        @Test
        @DisplayName("should sum a single-element list")
        void sumSingleElement() {
            assertEquals(42, calculator.sum(Collections.singletonList(42)));
        }

        @Test
        @DisplayName("should sum an empty list")
        void sumEmptyList() {
            assertEquals(0, calculator.sum(Collections.emptyList()));
        }

        @Test
        @DisplayName("should sum a list with negative numbers")
        void sumWithNegativeNumbers() {
            List<Integer> numbers = Arrays.asList(-10, 20, -30, 40);
            assertEquals(20, calculator.sum(numbers));
        }

        @Test
        @DisplayName("should sum a list with zero")
        void sumWithZero() {
            List<Integer> numbers = Arrays.asList(0, 0, 0);
            assertEquals(0, calculator.sum(numbers));
        }
    }

    // ============================================================
    // Average Tests
    // ============================================================

    @Nested
    @DisplayName("Average")
    class AverageTests {

        @Test
        @DisplayName("should calculate average of numbers")
        void averageOfNumbers() {
            List<Integer> numbers = Arrays.asList(10, 20, 30);
            assertEquals(20.0, calculator.average(numbers), 0.001);
        }

        @Test
        @DisplayName("should calculate average with decimal result")
        void averageWithDecimalResult() {
            List<Integer> numbers = Arrays.asList(1, 2);
            assertEquals(1.5, calculator.average(numbers), 0.001);
        }

        @Test
        @DisplayName("should calculate average of a single number")
        void averageOfSingleNumber() {
            assertEquals(42.0, calculator.average(Collections.singletonList(42)), 0.001);
        }

        @Test
        @DisplayName("should calculate average with negative numbers")
        void averageWithNegativeNumbers() {
            List<Integer> numbers = Arrays.asList(-10, 0, 10);
            assertEquals(0.0, calculator.average(numbers), 0.001);
        }

        @Test
        @DisplayName("should throw IllegalArgumentException for null list")
        void averageOfNull() {
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> calculator.average(null)
            );
            assertEquals("Cannot calculate average of empty list", exception.getMessage());
        }

        @Test
        @DisplayName("should throw IllegalArgumentException for empty list")
        void averageOfEmptyList() {
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> calculator.average(Collections.emptyList())
            );
            assertEquals("Cannot calculate average of empty list", exception.getMessage());
        }
    }
}
