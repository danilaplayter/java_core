package ru.mentee.power.methods.recursion;

import java.util.HashMap;
import java.util.Map;

public class RecursionExercises {

  private static Map<Integer, Long> fibCache = new HashMap<>(Map.of());

  static {
    fibCache.put(0, 0L);
    fibCache.put(1, 1L);
  }


  public static long factorial(int n) {
    if (n == -1) {
      return -1;
    }
    if (n == 0 || n == 1) {
      return 1;
    }
    return n * factorial(n - 1);
  }

  public static long fibonacci(int n) {
    if (n == -1) {
      throw new IllegalArgumentException("Числа Фибоначчи  начинаются с 0");
    }
    if (n <= 1) {
      return n;
    }
    return fibonacci(n - 1) + fibonacci(n - 2);
  }

  public static long fibonacciOptimized(int n) {
    if (n < 0) {
      return -1;
    }
    if (fibCache.containsKey(n)) {
      return fibCache.get(n);
    }
    long result = fibonacci(n - 1) + fibonacci(n - 2);
    fibCache.put(n, result);
    return result;
  }

  public static boolean isPalindrome(String str) {
    char[] string = str.trim().replaceAll("\\s", "").toLowerCase().toCharArray();
    for (int i = 0; i < string.length / 2; i++) {
      if (!(string[string.length - 1 - i] == string[i])) {
        return false;
      }
    }
    return true;
  }

  public static int sumOfDigits(int n) {
    if (n < 10) {
      return n;
    }
    return n % 10 + sumOfDigits(n / 10);
  }

  public static double power(double base, int exponent) {

    if (exponent == 0) {
      return 1;
    }

    if (exponent < 0) {
      return 1 / power(base, -exponent);
    }

    if (exponent % 2 == 0) {
      double halfPower = power(base, exponent / 2);
      return halfPower * halfPower;
    } else {
      return base * power(base, exponent - 1);
    }
  }

  public static int gcd(int a, int b) {

    if (b == 0) {
      return Math.abs(a);
    }

    return gcd(b, a % b);
  }

  public static void reverseArray(int[] array, int start, int end) {

    if (start >= end) {
      return;
    }

    int temp = array[start];
    array[start] = array[end];
    array[end] = temp;

    reverseArray(array, start + 1, end - 1);
  }
}