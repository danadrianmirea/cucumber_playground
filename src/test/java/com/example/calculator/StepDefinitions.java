package com.example.calculator;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

    private Calculator calculator;
    private int a, b, result;
    private double doubleResult;
    private List<Integer> numbers;
    private String expression;
    private Exception thrownException;
    private boolean calculatorOn;

    // ============================================================
    // Background steps
    // ============================================================

    @Given("I have a calculator")
    public void i_have_a_calculator() {
        calculator = new Calculator();
        thrownException = null;
        calculatorOn = false;
    }

    @Given("I have turned on the calculator")
    public void i_have_turned_on_the_calculator() {
        calculatorOn = true;
    }

    // ============================================================
    // Basic operations - Given steps
    // ============================================================

    @Given("I have two numbers {int} and {int}")
    public void i_have_two_numbers(int x, int y) {
        a = x;
        b = y;
    }

    // ============================================================
    // Basic operations - When steps
    // ============================================================

    @When("I add {int} and {int}")
    public void i_add(int x, int y) {
        a = x;
        b = y;
        result = calculator.add(a, b);
    }

    @When("I subtract {int} from {int}")
    public void i_subtract_from(int subtrahend, int minuend) {
        a = minuend;
        b = subtrahend;
        result = calculator.subtract(a, b);
    }

    @When("I multiply {int} and {int}")
    public void i_multiply(int x, int y) {
        a = x;
        b = y;
        result = calculator.multiply(a, b);
    }

    @When("I divide {int} by {int}")
    public void i_divide(int dividend, int divisor) {
        a = dividend;
        b = divisor;
        try {
            result = calculator.divide(a, b);
            thrownException = null;
        } catch (Exception e) {
            thrownException = e;
        }
    }

    @When("I raise {int} to the power of {int}")
    public void i_raise_to_power(int base, int exponent) {
        a = base;
        b = exponent;
        result = calculator.power(a, b);
    }

    @When("I calculate the square root of {int}")
    public void i_calculate_sqrt(int value) {
        try {
            doubleResult = calculator.sqrt(value);
            thrownException = null;
        } catch (Exception e) {
            thrownException = e;
        }
    }

    // ============================================================
    // Then steps
    // ============================================================

    @Then("the result should be {int}")
    public void the_result_should_be(int expected) {
        assertEquals(expected, result);
    }

    @Then("the result should be approximately {double}")
    public void the_result_should_be_approximately(double expected) {
        assertEquals(expected, doubleResult, 0.001);
    }

    @Then("an arithmetic error should be thrown with message {string}")
    public void arithmetic_error_thrown(String expectedMessage) {
        assertNotNull("Expected an exception to be thrown", thrownException);
        assertTrue("Expected ArithmeticException", thrownException instanceof ArithmeticException);
        assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Then("an illegal argument error should be thrown with message {string}")
    public void illegal_argument_error_thrown(String expectedMessage) {
        assertNotNull("Expected an exception to be thrown", thrownException);
        assertTrue("Expected IllegalArgumentException", thrownException instanceof IllegalArgumentException);
        assertEquals(expectedMessage, thrownException.getMessage());
    }

    // ============================================================
    // Data Table steps
    // ============================================================

    @Given("I have the following numbers:")
    public void i_have_the_following_numbers(DataTable dataTable) {
        // Convert the single-column data table to a list of integers
        List<List<String>> rows = dataTable.asLists(String.class);
        numbers = rows.stream()
                .map(row -> Integer.parseInt(row.get(0)))
                .toList();
    }

    @When("I sum them")
    public void i_sum_them() {
        result = calculator.sum(numbers);
    }

    @When("I calculate the average")
    public void i_calculate_the_average() {
        doubleResult = calculator.average(numbers);
    }

    // ============================================================
    // Doc String steps
    // ============================================================

    @Given("I have a calculation expression:")
    public void i_have_a_calculation_expression(String docString) {
        calculator = new Calculator();
        expression = docString.trim();
    }

    @When("I evaluate the expression")
    public void i_evaluate_the_expression() {
        String[] lines = expression.split("\n");
        String operation = lines[0].trim().toLowerCase();
        int x = Integer.parseInt(lines[1].trim());
        int y = Integer.parseInt(lines[2].trim());

        switch (operation) {
            case "add":
                result = calculator.add(x, y);
                break;
            case "subtract":
                result = calculator.subtract(x, y);
                break;
            case "multiply":
                result = calculator.multiply(x, y);
                break;
            case "divide":
                result = calculator.divide(x, y);
                break;
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }
}
