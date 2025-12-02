package com.example.calculator;

public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        int a = 2;
        int b = 3;
        int result = calculator.add(a, b);
        System.out.println("Result: " + result);
    }
}
