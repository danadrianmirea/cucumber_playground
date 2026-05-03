package com.example.calculator;

import java.util.List;

/**
 * An interface representing an audit logger.
 * This is used to demonstrate mocking with Mockito.
 */
public interface AuditLogger {
    void log(String operation, int a, int b, int result);
    void log(String operation, int value, double result);
    void log(String operation, List<Integer> numbers, int result);
    void log(String operation, List<Integer> numbers, double result);
}
