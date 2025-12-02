package com.example.calculator;

import io.cucumber.java.en.*;
import static org.junit.Assert.*;

public class StepDefinitions {

    private int a, b, result;
    private Calculator calculator = new Calculator();

    @Given("I have two numbers {int} and {int}")
    public void i_have_two_numbers(int x, int y) {
        a = x;
        b = y;
    }

    @When("I add them")
    public void i_add_them() {
        result = calculator.add(a, b);
    }

    @Then("the result should be {int}")
    public void the_result_should_be(int expected) {
        assertEquals(expected, result);
    }
}