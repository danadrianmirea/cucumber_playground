package com.example.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

/**
 * Mockito-based tests demonstrating various mocking techniques.
 * This is the Java equivalent of CppUTest's mocking capabilities.
 *
 * Demonstrates:
 * - @Mock annotation for creating mock objects
 * - @InjectMocks for automatic dependency injection
 * - @Spy for partial mocking
 * - @Captor for capturing arguments
 * - Stubbing (when/thenReturn, when/thenThrow)
 * - Verification (verify, times, never, atLeast)
 * - Argument matchers (anyInt, anyString, eq)
 * - Exception simulation
 * - Behavior-driven development (BDD) style
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CalculatorService Mockito Tests")
class CalculatorServiceMockTest {

    // ============================================================
    // Basic Mocking with @Mock and @InjectMocks
    // ============================================================

    @Nested
    @DisplayName("Basic Mocking with @Mock and @InjectMocks")
    class BasicMockingTests {

        @Mock
        private Calculator calculator;

        @Mock
        private AuditLogger auditLogger;

        @InjectMocks
        private CalculatorService calculatorService;

        @Test
        @DisplayName("should stub a method to return a specific value")
        void stubMethodReturnValue() {
            // Arrange - stub the calculator.add method
            when(calculator.add(2, 3)).thenReturn(5);

            // Act
            int result = calculatorService.performAddition(2, 3);

            // Assert
            assertEquals(5, result);
        }

        @Test
        @DisplayName("should stub a method to throw an exception")
        void stubMethodThrowException() {
            // Arrange - stub divide to throw ArithmeticException
            when(calculator.divide(10, 0)).thenThrow(new ArithmeticException("Division by zero is not allowed"));

            // Act & Assert
            ArithmeticException exception = assertThrows(
                    ArithmeticException.class,
                    () -> calculatorService.performDivision(10, 0)
            );
            assertEquals("Division by zero is not allowed", exception.getMessage());
        }

        @Test
        @DisplayName("should stub a method to throw an exception (using class-based thenThrow)")
        void stubMethodThrowExceptionByClass() {
            // Arrange - stub sqrt to throw IllegalArgumentException
            when(calculator.sqrt(-1)).thenThrow(IllegalArgumentException.class);

            // Act & Assert
            assertThrows(
                    IllegalArgumentException.class,
                    () -> calculatorService.performSquareRoot(-1)
            );
        }

        @Test
        @DisplayName("should stub a method with argument matchers")
        void stubWithArgumentMatchers() {
            // Arrange - stub add with any integers
            when(calculator.add(anyInt(), anyInt())).thenReturn(42);

            // Act
            int result1 = calculatorService.performAddition(1, 2);
            int result2 = calculatorService.performAddition(100, 200);
            int result3 = calculatorService.performAddition(-5, 10);

            // Assert
            assertEquals(42, result1);
            assertEquals(42, result2);
            assertEquals(42, result3);
        }

        @Test
        @DisplayName("should stub with mixed specific and matcher arguments")
        void stubWithMixedArguments() {
            // Arrange - stub add with specific first arg and any second arg
            when(calculator.add(eq(0), anyInt())).thenReturn(0);

            // Act
            int result1 = calculatorService.performAddition(0, 5);
            int result2 = calculatorService.performAddition(0, -10);
            int result3 = calculatorService.performAddition(1, 5); // not stubbed

            // Assert
            assertEquals(0, result1);
            assertEquals(0, result2);
            assertEquals(0, result3); // default mock returns 0 for int
        }

        @Test
        @DisplayName("should stub consecutive calls to return different values")
        void stubConsecutiveCalls() {
            // Arrange - stub add to return different values on consecutive calls
            when(calculator.add(anyInt(), anyInt()))
                    .thenReturn(1)
                    .thenReturn(2)
                    .thenReturn(3);

            // Act
            int first = calculatorService.performAddition(0, 0);
            int second = calculatorService.performAddition(0, 0);
            int third = calculatorService.performAddition(0, 0);
            int fourth = calculatorService.performAddition(0, 0); // beyond stubbed values

            // Assert
            assertEquals(1, first);
            assertEquals(2, second);
            assertEquals(3, third);
            assertEquals(3, fourth); // last stubbed value is reused
        }

        @Test
        @DisplayName("should stub void method to do nothing")
        void stubVoidMethodDoNothing() {
            // Arrange - stub the void auditLogger.log method
            doNothing().when(auditLogger).log(anyString(), anyInt(), anyInt(), anyInt());
            when(calculator.add(2, 3)).thenReturn(5);

            // Act - should not throw
            int result = calculatorService.performAddition(2, 3);

            // Assert
            assertEquals(5, result);
        }

        @Test
        @DisplayName("should stub void method to throw exception")
        void stubVoidMethodThrowException() {
            // Arrange - stub auditLogger to throw on specific input
            doThrow(new RuntimeException("Audit failed"))
                    .when(auditLogger)
                    .log(eq("add"), anyInt(), anyInt(), anyInt());
            when(calculator.add(2, 3)).thenReturn(5);

            // Act & Assert
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> calculatorService.performAddition(2, 3)
            );
            assertEquals("Audit failed", exception.getMessage());
        }

        @Test
        @DisplayName("should verify method was called")
        void verifyMethodCalled() {
            // Arrange
            when(calculator.add(2, 3)).thenReturn(5);

            // Act
            calculatorService.performAddition(2, 3);

            // Assert - verify the method was called with exact arguments
            verify(calculator).add(2, 3);
        }

        @Test
        @DisplayName("should verify method was called with matchers")
        void verifyMethodCalledWithMatchers() {
            // Arrange
            when(calculator.add(anyInt(), anyInt())).thenReturn(5);

            // Act
            calculatorService.performAddition(2, 3);

            // Assert - verify with matchers
            verify(calculator).add(anyInt(), anyInt());
        }

        @Test
        @DisplayName("should verify method was called a specific number of times")
        void verifyMethodCalledTimes() {
            // Arrange
            when(calculator.add(anyInt(), anyInt())).thenReturn(5);

            // Act
            calculatorService.performAddition(1, 2);
            calculatorService.performAddition(3, 4);
            calculatorService.performAddition(5, 6);

            // Assert - verify exactly 3 calls
            verify(calculator, times(3)).add(anyInt(), anyInt());
        }

        @Test
        @DisplayName("should verify method was never called")
        void verifyMethodNeverCalled() {
            // Act - only call subtract, not add
            when(calculator.subtract(10, 5)).thenReturn(5);
            calculatorService.performSubtraction(10, 5);

            // Assert - verify add was never called
            verify(calculator, never()).add(anyInt(), anyInt());
        }

        @Test
        @DisplayName("should verify method was called at least/at most N times")
        void verifyMethodCalledAtLeastAtMost() {
            // Arrange
            when(calculator.add(anyInt(), anyInt())).thenReturn(5);

            // Act
            calculatorService.performAddition(1, 2);
            calculatorService.performAddition(3, 4);

            // Assert
            verify(calculator, atLeast(1)).add(anyInt(), anyInt());
            verify(calculator, atMost(3)).add(anyInt(), anyInt());
        }

        @Test
        @DisplayName("should verify no more interactions on mock")
        void verifyNoMoreInteractions() {
            // Arrange
            when(calculator.add(2, 3)).thenReturn(5);

            // Act
            calculatorService.performAddition(2, 3);

            // Assert - verify add was called and no other interactions
            verify(calculator).add(2, 3);
            Mockito.verifyNoMoreInteractions(calculator);
        }

        @Test
        @DisplayName("should verify zero interactions on mock")
        void verifyZeroInteractions() {
            // Act - don't interact with calculator at all

            // Assert
            Mockito.verifyNoInteractions(calculator);
        }

        @Test
        @DisplayName("should verify order of interactions")
        void verifyOrderOfInteractions() {
            // Arrange
            when(calculator.add(2, 3)).thenReturn(5);
            when(calculator.subtract(10, 5)).thenReturn(5);

            // Act
            calculatorService.performAddition(2, 3);
            calculatorService.performSubtraction(10, 5);

            // Assert - verify the order
            InOrder inOrder = inOrder(calculator);
            inOrder.verify(calculator).add(2, 3);
            inOrder.verify(calculator).subtract(10, 5);
        }

        @Test
        @DisplayName("should verify interactions across multiple mocks in order")
        void verifyOrderAcrossMocks() {
            // Arrange
            when(calculator.add(2, 3)).thenReturn(5);

            // Act
            calculatorService.performAddition(2, 3);

            // Assert - verify calculator was called before auditLogger
            InOrder inOrder = inOrder(calculator, auditLogger);
            inOrder.verify(calculator).add(2, 3);
            inOrder.verify(auditLogger).log(anyString(), anyInt(), anyInt(), anyInt());
        }

        @Test
        @DisplayName("should verify timeout on method call")
        void verifyTimeout() {
            // Arrange
            when(calculator.add(2, 3)).thenReturn(5);

            // Act
            calculatorService.performAddition(2, 3);

            // Assert - verify with timeout (100ms)
            verify(calculator, timeout(100)).add(2, 3);
        }
    }

    // ============================================================
    // Argument Captor Tests
    // ============================================================

    @Nested
    @DisplayName("Argument Captor with @Captor")
    class ArgumentCaptorTests {

        @Mock
        private Calculator calculator;

        @Mock
        private AuditLogger auditLogger;

        @InjectMocks
        private CalculatorService calculatorService;

        @Captor
        private ArgumentCaptor<String> stringCaptor;

        @Captor
        private ArgumentCaptor<Integer> intCaptor;

        @Captor
        private ArgumentCaptor<List<Integer>> listCaptor;

        @Test
        @DisplayName("should capture string argument passed to mock")
        void captureStringArgument() {
            // Arrange
            when(calculator.add(2, 3)).thenReturn(5);

            // Act
            calculatorService.performAddition(2, 3);

            // Assert - capture the operation name passed to auditLogger
            verify(auditLogger).log(stringCaptor.capture(), anyInt(), anyInt(), anyInt());
            assertEquals("add", stringCaptor.getValue());
        }

        @Test
        @DisplayName("should capture multiple arguments passed to mock")
        void captureMultipleArguments() {
            // Arrange
            when(calculator.add(2, 3)).thenReturn(5);

            // Act
            calculatorService.performAddition(2, 3);

            // Assert - capture all arguments
            verify(auditLogger).log(
                    stringCaptor.capture(),
                    intCaptor.capture(),
                    intCaptor.capture(),
                    intCaptor.capture()
            );

            assertEquals("add", stringCaptor.getValue());
            List<Integer> capturedInts = intCaptor.getAllValues();
            assertEquals(2, capturedInts.get(0)); // a
            assertEquals(3, capturedInts.get(1)); // b
            assertEquals(5, capturedInts.get(2)); // result
        }

        @Test
        @DisplayName("should capture list argument passed to mock")
        void captureListArgument() {
            // Arrange
            List<Integer> numbers = Arrays.asList(10, 20, 30);
            when(calculator.sum(numbers)).thenReturn(60);

            // Act
            calculatorService.performSum(numbers);

            // Assert - capture the list passed to auditLogger
            verify(auditLogger).log(anyString(), listCaptor.capture(), anyInt());
            assertEquals(Arrays.asList(10, 20, 30), listCaptor.getValue());
        }

        @Test
        @DisplayName("should capture all invocations of a method")
        void captureAllInvocations() {
            // Arrange
            when(calculator.add(anyInt(), anyInt())).thenReturn(5);

            // Act
            calculatorService.performAddition(1, 2);
            calculatorService.performAddition(3, 4);
            calculatorService.performAddition(5, 6);

            // Assert - capture all arguments from all invocations
            verify(auditLogger, times(3)).log(
                    stringCaptor.capture(),
                    intCaptor.capture(),
                    intCaptor.capture(),
                    intCaptor.capture()
            );

            List<String> capturedStrings = stringCaptor.getAllValues();
            assertEquals(3, capturedStrings.size());
            capturedStrings.forEach(s -> assertEquals("add", s));
        }
    }

    // ============================================================
    // Spy Tests (Partial Mocking)
    // ============================================================

    @Nested
    @DisplayName("Spy (Partial Mocking) with @Spy")
    class SpyTests {

        @Spy
        private Calculator calculator = new Calculator();

        @Mock
        private AuditLogger auditLogger;

        @InjectMocks
        private CalculatorService calculatorService;

        @Test
        @DisplayName("should call real method by default on spy")
        void spyCallsRealMethodByDefault() {
            // Act - spy calls the real add method
            int result = calculatorService.performAddition(2, 3);

            // Assert - real method was called
            assertEquals(5, result);
        }

        @Test
        @DisplayName("should stub specific method on spy while keeping others real")
        void spyStubSpecificMethod() {
            // Arrange - stub only the divide method on spy
            doReturn(100).when(calculator).divide(anyInt(), anyInt());

            // Act
            int result = calculatorService.performDivision(10, 2);

            // Assert - stubbed value returned instead of real calculation
            assertEquals(100, result);
        }

        @Test
        @DisplayName("should verify spy was called with real implementation")
        void spyVerifyRealMethodCalled() {
            // Act
            calculatorService.performAddition(2, 3);

            // Assert - verify the real method was called
            verify(calculator).add(2, 3);
        }

        @Test
        @DisplayName("should throw exception from spy method")
        void spyThrowException() {
            // Arrange - stub spy to throw
            doThrow(new ArithmeticException("Simulated failure"))
                    .when(calculator).divide(anyInt(), anyInt());

            // Act & Assert
            ArithmeticException exception = assertThrows(
                    ArithmeticException.class,
                    () -> calculatorService.performDivision(10, 2)
            );
            assertEquals("Simulated failure", exception.getMessage());
        }

        @Test
        @DisplayName("should reset spy and restore real behavior")
        void spyReset() {
            // Arrange - stub then reset
            doReturn(999).when(calculator).add(anyInt(), anyInt());
            int stubbedResult = calculatorService.performAddition(2, 3);
            assertEquals(999, stubbedResult);

            // Reset the spy
            reset(calculator);

            // Act - after reset, real method is called again
            int realResult = calculatorService.performAddition(2, 3);

            // Assert
            assertEquals(5, realResult);
        }
    }

    // ============================================================
    // BDD Style Tests (Behavior-Driven Development)
    // ============================================================

    @Nested
    @DisplayName("BDD Style with BDDMockito")
    class BddStyleTests {

        @Mock
        private Calculator calculator;

        @Mock
        private AuditLogger auditLogger;

        @InjectMocks
        private CalculatorService calculatorService;

        @Test
        @DisplayName("should use BDD style given/willReturn pattern")
        void bddStyleGivenWillReturn() {
            // Given - using BDDMockito static imports
            given(calculator.add(2, 3)).willReturn(5);

            // When
            int result = calculatorService.performAddition(2, 3);

            // Then
            assertEquals(5, result);
            then(calculator).should(times(1)).add(2, 3);
            then(auditLogger).should(times(1)).log(anyString(), anyInt(), anyInt(), anyInt());
        }

        @Test
        @DisplayName("should use BDD style with willThrow")
        void bddStyleWillThrow() {
            // Given
            given(calculator.divide(10, 0)).willThrow(new ArithmeticException("Division by zero"));

            // When
            ArithmeticException exception = assertThrows(
                    ArithmeticException.class,
                    () -> calculatorService.performDivision(10, 0)
            );

            // Then
            assertEquals("Division by zero", exception.getMessage());
            then(calculator).should(times(1)).divide(10, 0);
        }

        @Test
        @DisplayName("should use BDD style with willAnswer for custom behavior")
        void bddStyleWillAnswer() {
            // Given - custom answer that doubles the result
            given(calculator.add(anyInt(), anyInt())).willAnswer(invocation -> {
                int a = invocation.getArgument(0);
                int b = invocation.getArgument(1);
                return (a + b) * 2; // double the sum
            });

            // When
            int result = calculatorService.performAddition(2, 3);

            // Then
            assertEquals(10, result); // (2+3)*2 = 10
        }

        @Test
        @DisplayName("should use BDD style with shouldHaveNoMoreInteractions")
        void bddStyleNoMoreInteractions() {
            // Given
            given(calculator.add(2, 3)).willReturn(5);

            // When
            calculatorService.performAddition(2, 3);

            // Then
            then(calculator).should(times(1)).add(2, 3);
            then(calculator).shouldHaveNoMoreInteractions();
        }
    }

    // ============================================================
    // Advanced Mocking Techniques
    // ============================================================

    @Nested
    @DisplayName("Advanced Mocking Techniques")
    class AdvancedMockingTests {

        @Mock
        private Calculator calculator;

        @Mock
        private AuditLogger auditLogger;

        @InjectMocks
        private CalculatorService calculatorService;

        @Test
        @DisplayName("should use Answer interface for dynamic return values")
        void answerInterface() {
            // Arrange - use Answer to compute return value based on input
            when(calculator.add(anyInt(), anyInt())).thenAnswer(invocation -> {
                int a = invocation.getArgument(0);
                int b = invocation.getArgument(1);
                return a + b; // actually perform the real calculation
            });

            // Act
            int result1 = calculatorService.performAddition(2, 3);
            int result2 = calculatorService.performAddition(10, 20);
            int result3 = calculatorService.performAddition(-5, 5);

            // Assert
            assertEquals(5, result1);
            assertEquals(30, result2);
            assertEquals(0, result3);
        }

        @Test
        @DisplayName("should use Answer to simulate a slow operation")
        void answerSimulateSlowOperation() throws InterruptedException {
            // Arrange - simulate a slow operation
            when(calculator.add(anyInt(), anyInt())).thenAnswer(invocation -> {
                Thread.sleep(50); // simulate delay
                int a = invocation.getArgument(0);
                int b = invocation.getArgument(1);
                return a + b;
            });

            // Act
            long start = System.currentTimeMillis();
            int result = calculatorService.performAddition(2, 3);
            long duration = System.currentTimeMillis() - start;

            // Assert
            assertEquals(5, result);
            assertTrue(duration >= 50, "Should have taken at least 50ms");
        }

        @Test
        @DisplayName("should use lenient stubbing to avoid unnecessary stubbing warnings")
        void lenientStubbing() {
            // Use lenient() to avoid "UnnecessaryStubbingException" for unused stubs
            lenient().when(calculator.add(999, 999)).thenReturn(999); // this stub is never used
            when(calculator.add(2, 3)).thenReturn(5);

            // Act
            int result = calculatorService.performAddition(2, 3);

            // Assert
            assertEquals(5, result);
        }

        @Test
        @DisplayName("should verify with custom description on failure")
        void verifyWithDescription() {
            // Arrange
            when(calculator.add(2, 3)).thenReturn(5);

            // Act
            calculatorService.performAddition(2, 3);

            // Assert with custom assertion description
            verify(calculator, description("Calculator.add() should have been called with 2 and 3"))
                    .add(2, 3);
        }

        @Test
        @DisplayName("should verify with timeout and description")
        void verifyWithTimeoutAndDescription() {
            // Arrange
            when(calculator.add(2, 3)).thenReturn(5);

            // Act
            calculatorService.performAddition(2, 3);

            // Assert with timeout and description
            verify(calculator, timeout(100).description("add should complete within 100ms"))
                    .add(2, 3);
        }
    }

    // ============================================================
    // Integration-style Tests (Mocking only the dependency)
    // ============================================================

    @Nested
    @DisplayName("Integration-style Tests with Mocked Dependencies")
    class IntegrationStyleTests {

        @Mock
        private AuditLogger auditLogger;

        private Calculator realCalculator;
        private CalculatorService calculatorService;

        @BeforeEach
        void setUp() {
            // Use real Calculator but mock AuditLogger
            realCalculator = new Calculator();
            calculatorService = new CalculatorService(realCalculator, auditLogger);
        }

        @Test
        @DisplayName("should use real calculator with mocked audit logger")
        void realCalculatorMockedLogger() {
            // Act - uses real calculator, but auditLogger is mocked
            int result = calculatorService.performAddition(2, 3);

            // Assert - real calculation
            assertEquals(5, result);

            // Verify audit was called
            verify(auditLogger).log("add", 2, 3, 5);
        }

        @Test
        @DisplayName("should verify audit logger receives correct values from real calculation")
        void verifyAuditLoggerReceivesCorrectValues() {
            // Act
            calculatorService.performDivision(10, 3);

            // Assert - verify auditLogger received the correct truncated result
            verify(auditLogger).log("divide", 10, 3, 3);
        }

        @Test
        @DisplayName("should verify audit logger for sqrt with real calculation")
        void verifyAuditLoggerForSqrt() {
            // Act
            calculatorService.performSquareRoot(9);

            // Assert
            verify(auditLogger).log("sqrt", 9, 3.0);
        }

        @Test
        @DisplayName("should verify audit logger for sum with real calculation")
        void verifyAuditLoggerForSum() {
            // Arrange
            List<Integer> numbers = Arrays.asList(10, 20, 30, 40);

            // Act
            calculatorService.performSum(numbers);

            // Assert
            verify(auditLogger).log("sum", numbers, 100);
        }

        @Test
        @DisplayName("should verify audit logger for average with real calculation")
        void verifyAuditLoggerForAverage() {
            // Arrange
            List<Integer> numbers = Arrays.asList(1, 2);

            // Act
            calculatorService.performAverage(numbers);

            // Assert
            verify(auditLogger).log("average", numbers, 1.5);
        }

        @Test
        @DisplayName("should propagate real exception from calculator")
        void propagateRealException() {
            // Act & Assert - real calculator throws on divide by zero
            ArithmeticException exception = assertThrows(
                    ArithmeticException.class,
                    () -> calculatorService.performDivision(10, 0)
            );
            assertEquals("Division by zero is not allowed", exception.getMessage());

            // Verify auditLogger was NOT called (exception happened before logging)
            verify(auditLogger, never()).log(anyString(), anyInt(), anyInt(), anyInt());
        }

        @Test
        @DisplayName("should propagate real IllegalArgumentException from sqrt")
        void propagateRealIllegalArgument() {
            // Act & Assert
            assertThrows(
                    IllegalArgumentException.class,
                    () -> calculatorService.performSquareRoot(-1)
            );

            // Verify auditLogger was NOT called
            verify(auditLogger, never()).log(anyString(), anyInt(), anyDouble());
        }
    }

}
