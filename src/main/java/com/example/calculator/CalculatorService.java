package com.example.calculator;

import java.util.List;

/**
 * A service layer that depends on Calculator.
 * This demonstrates dependency injection and is used for mocking examples.
 */
public class CalculatorService {

    private final Calculator calculator;
    private final AuditLogger auditLogger;

    public CalculatorService(Calculator calculator, AuditLogger auditLogger) {
        this.calculator = calculator;
        this.auditLogger = auditLogger;
    }

    public int performAddition(int a, int b) {
        int result = calculator.add(a, b);
        auditLogger.log("add", a, b, result);
        return result;
    }

    public int performSubtraction(int a, int b) {
        int result = calculator.subtract(a, b);
        auditLogger.log("subtract", a, b, result);
        return result;
    }

    public int performMultiplication(int a, int b) {
        int result = calculator.multiply(a, b);
        auditLogger.log("multiply", a, b, result);
        return result;
    }

    public int performDivision(int a, int b) {
        int result = calculator.divide(a, b);
        auditLogger.log("divide", a, b, result);
        return result;
    }

    public int performPower(int base, int exponent) {
        int result = calculator.power(base, exponent);
        auditLogger.log("power", base, exponent, result);
        return result;
    }

    public double performSquareRoot(int value) {
        double result = calculator.sqrt(value);
        auditLogger.log("sqrt", value, result);
        return result;
    }

    public int performSum(List<Integer> numbers) {
        int result = calculator.sum(numbers);
        auditLogger.log("sum", numbers, result);
        return result;
    }

    public double performAverage(List<Integer> numbers) {
        double result = calculator.average(numbers);
        auditLogger.log("average", numbers, result);
        return result;
    }
}
