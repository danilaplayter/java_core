package ru.mentee.power.loop;

import java.util.HashMap;
import java.util.Map;

public class FibonacciCalculator {

  private final Map<Integer, Long> cache = new HashMap<>();

  public long fibonacciRecursive(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("n must be non-negative");
    }
    if (n == 0) return 0;
    if (n == 1) return 1;
    return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
  }

  public long fibonacciIterative(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("n must be non-negative");
    }
    if (n == 0) return 0;
    if (n == 1) return 1;
    long prevPrev = 0;
    long prev = 1;
    long current = 0;
    for (int i = 2; i <= n; i++) {
      current = prevPrev + prev;
      prevPrev = prev;
      prev = current;
    }
    return current;
  }

  public long fibonacciWithCache(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("n must be non-negative");
    }
    if (n == 0) return 0;
    if (n == 1) return 1;
    Long cached = cache.get(n);
    if (cached != null) {
      return cached;
    }
    long result = fibonacciWithCache(n - 1) + fibonacciWithCache(n - 2);
    cache.put(n, result);
    return result;
  }

  public long[] generateFibonacciSequence(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("n не может быть отрицательным.");
    }
    if (n == 0) {
      return new long[0];
    }
    long[] sequence = new long[n];
    if (n >= 1) {
      sequence[0] = 0;
    }
    if (n >= 2) {
      sequence[1] = 1;
    }
    for (int i = 2; i < n; i++) {
      sequence[i] = sequence[i - 1] + sequence[i - 2];
    }
    return sequence;
  }

  public boolean isFibonacciNumber(long number) {
    if (number < 0) {
      return false;
    }
    if (number == 0 || number == 1) {
      return true;
    }
    long x = 5 * number * number;
    long test1 = x + 4;
    long test2 = x - 4;
    return isPerfectSquare(test1) || isPerfectSquare(test2);
  }

  private boolean isPerfectSquare(long num) {
    if (num < 0) {
      return false;
    }
    long sqrt = (long) Math.sqrt(num);
    return sqrt * sqrt == num;
  }

  public static void main(String[] args) {
    FibonacciCalculator calculator = new FibonacciCalculator();

    System.out.println("Последовательность Фибоначчи (первые 10 чисел):");
    long[] sequence = calculator.generateFibonacciSequence(10);
    for (int i = 0; i < sequence.length; i++) {
      System.out.println("F(" + i + ") = " + sequence[i]);
    }

    System.out.println("\nПроверка различных методов вычисления F(10):");
    System.out.println("Рекурсивный метод: " + calculator.fibonacciRecursive(10));
    System.out.println("Итеративный метод: " + calculator.fibonacciIterative(10));
    System.out.println("Метод с кэшированием: " + calculator.fibonacciWithCache(10));

    System.out.println("\nПроверка, является ли число числом Фибоначчи:");
    long[] testNumbers = {8, 10, 13, 15, 21};
    for (long num : testNumbers) {
      System.out.println(num + ": " + calculator.isFibonacciNumber(num));
    }
  }
}