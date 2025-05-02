package ru.mentee.power.collections.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionAnalyzer {

  public static List<String> findLongStrings(Collection<String> strings, int minLength) {
    List<String> result = new ArrayList<>();

    if (strings == null || minLength < 0) {
      return result;
    }

    for (String str : strings) {
      if ((str != null) && (str.length() > minLength) && (!str.trim().isEmpty())) {
        result.add(str);
      }
    }

    return result;
  }

  public static int countNumbersWithDigitSumGreaterThan(Collection<Integer> numbers,
      int threshold) {
    if (numbers == null) {
      return 0;
    }

    int count = 0;
    for (Integer num : numbers) {
      if (num != null && calculateDigitSum(num) > threshold) {
        count++;
      }
    }
    return count;
  }

  static int calculateDigitSum(int number) {
    number = Math.abs(number); // Обрабатываем отрицательные числа
    int sum = 0;

    while (number > 0) {
      sum += number % 10;
      number /= 10;
    }

    return sum;
  }
}