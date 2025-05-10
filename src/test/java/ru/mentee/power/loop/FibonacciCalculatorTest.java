package ru.mentee.power.loop;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class FibonacciCalculatorTest {

  private FibonacciCalculator calculator;

  @BeforeEach
  void setUp() {
    calculator = new FibonacciCalculator();
  }

  @Test
  void testFibonacciRecursiveSmallNumbers() {
    assertThat(calculator.fibonacciRecursive(0)).isEqualTo(0);
    assertThat(calculator.fibonacciRecursive(1)).isEqualTo(1);
    assertThat(calculator.fibonacciRecursive(2)).isEqualTo(1);
    assertThat(calculator.fibonacciRecursive(3)).isEqualTo(2);
    assertThat(calculator.fibonacciRecursive(4)).isEqualTo(3);
    assertThat(calculator.fibonacciRecursive(5)).isEqualTo(5);
    assertThat(calculator.fibonacciRecursive(10)).isEqualTo(55);
  }

  @Test
  void testFibonacciIterative() {
    assertThat(calculator.fibonacciIterative(0)).isEqualTo(0);
    assertThat(calculator.fibonacciIterative(1)).isEqualTo(1);
    assertThat(calculator.fibonacciIterative(10)).isEqualTo(55);
    assertThat(calculator.fibonacciIterative(20)).isEqualTo(6765);
    assertThat(calculator.fibonacciIterative(30)).isEqualTo(832040);
    assertThat(calculator.fibonacciIterative(40)).isEqualTo(102334155);
  }

  @Test
  void testFibonacciWithCache() {
    assertThat(calculator.fibonacciWithCache(0)).isEqualTo(0);
    assertThat(calculator.fibonacciWithCache(1)).isEqualTo(1);
    assertThat(calculator.fibonacciWithCache(10)).isEqualTo(55);
    assertThat(calculator.fibonacciWithCache(40)).isEqualTo(102334155);
    assertThat(calculator.fibonacciWithCache(45)).isEqualTo(1134903170);
  }

  @Test
  @Timeout(value = 5)
    // Тест должен выполниться за 5 секунд
  void testFibonacciRecursiveWithTimeout() {
    calculator.fibonacciRecursive(25);
  }

  @Test
  void testGenerateFibonacciSequence() {
    long[] expected = {0, 1, 1, 2, 3, 5, 8, 13, 21};
    assertThat(calculator.generateFibonacciSequence(9)).isEqualTo(expected);
  }

  @Test
  void testIsFibonacciNumber() {
    assertThat(calculator.isFibonacciNumber(0)).isTrue();
    assertThat(calculator.isFibonacciNumber(1)).isTrue();
    assertThat(calculator.isFibonacciNumber(2)).isTrue();
    assertThat(calculator.isFibonacciNumber(3)).isTrue();
    assertThat(calculator.isFibonacciNumber(4)).isFalse();
    assertThat(calculator.isFibonacciNumber(5)).isTrue();
    assertThat(calculator.isFibonacciNumber(6)).isFalse();
    assertThat(calculator.isFibonacciNumber(8)).isTrue();
    assertThat(calculator.isFibonacciNumber(10)).isFalse();
    assertThat(calculator.isFibonacciNumber(13)).isTrue();
    assertThat(calculator.isFibonacciNumber(21)).isTrue();
  }

  @Test
  void testNegativeInput() {
    // Проверка всех методов с отрицательным входом
    assertAll(() -> assertThrowsExactly(IllegalArgumentException.class,
            () -> calculator.fibonacciRecursive(-1)),

        () -> assertThrowsExactly(IllegalArgumentException.class,
            () -> calculator.fibonacciIterative(-5)),

        () -> assertThrowsExactly(IllegalArgumentException.class,
            () -> calculator.fibonacciWithCache(-10)),

        () -> assertThrowsExactly(IllegalArgumentException.class,
            () -> calculator.generateFibonacciSequence(-3)),

        () -> assertFalse(calculator.isFibonacciNumber(-144)));
  }

}