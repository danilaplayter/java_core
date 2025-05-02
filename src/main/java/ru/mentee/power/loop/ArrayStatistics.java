package ru.mentee.power.loop;

import java.util.Arrays;

public class ArrayStatistics {

  private final int[] data;

  public ArrayStatistics(int[] data) {
    this.data = Arrays.copyOf(data, data.length);
  }

  public int findMin() {

    if (data == null || data.length == 0) {
      return Integer.MAX_VALUE;
    }

    int min = data[0];

    for (int number : data) {
      if (number < min) {
        min = number;
      }
    }
    return min;

  }

  public int findMax() {
    if (data == null || data.length == 0) {
      return Integer.MAX_VALUE;
    }

    int max = data[0];

    for (int number : data) {
      if (number > max) {
        max = number;
      }
    }
    return max;
  }

  public int calculateSum() {
    int sum = 0;
    if (data == null || data.length == 0) {
      return Integer.MAX_VALUE;
    }
    for (int number : data) {
      sum += number;
    }
    return sum;
  }

  public double calculateAverage() {
    double average = 0.0;

    if (data == null || data.length == 0) {
      return Integer.MAX_VALUE;
    }

    for (int number : data) {
      average += number;
    }

    return (average / data.length);
  }

  public double calculateMedian() {
    if (data == null || data.length == 0) {
      return Integer.MAX_VALUE;
    }

    Arrays.sort(data);

    int middle = data.length / 2;

    if (data.length % 2 == 0) {
      return (data[middle - 1] + data[middle]) / 2.0;
    } else {
      return data[middle];
    }
  }

  public int findMode() {

    if (data == null || data.length == 0) {
      return 0;
    }
    Arrays.sort(data);

    int currentNum = data[0];
    int currentCount = 1;
    int maxNum = currentNum;
    int maxCount = currentCount;

    for (int number : data) {
      if (number == currentNum) {
        currentCount++;
      } else {
        if (currentCount > maxCount) {
          maxCount = currentCount;
          maxNum = currentNum;
        }
        currentNum = number;
        currentCount = 1;
      }
    }

    if (currentCount > maxCount) {
      maxNum = currentNum;
    }

    return maxNum;

  }

  public double calculateStandardDeviation() {
    if (data == null || data.length == 0 || data.length < 2) {
      return Integer.MAX_VALUE;
    }

    double sum = 0.0;
    for (int num : data) {
      sum += num;
    }
    double mean = sum / data.length;

    double squaredDifferencesSum = 0.0;
    for (int num : data) {
      squaredDifferencesSum += Math.pow(num - mean, 2);
    }

    double variance = squaredDifferencesSum / (data.length - 1);

    return Math.sqrt(variance);
  }

  public int countGreaterThan(int value) {
    if (data == null || data.length == 0) {
      return 0;
    }
    int count = 0;
    for (int number : data) {
      if (number > value) {
        count++;
      }
    }
    return count;
  }

  public int countLessThan(int value) {
    if (data == null || data.length == 0) {
      return 0;
    }
    int count = 0;
    for (int number : data) {
      if (number < value) {
        count++;
      }
    }
    return count;
  }

  public boolean contains(int value) {
    if (data == null || data.length == 0) {
      return false;
    }
    for (int number : data) {
      if (number == value) {
        return true;
      }
    }

    return false;
  }

  public void printStatisticsReport() {
    System.out.println("===== Статистический отчет =====");
    System.out.println("Размер массива: " + data.length);
    System.out.println("Минимальное значение: " + findMin());
    System.out.println("Максимальное значение: " + findMax());
    System.out.println("Сумма элементов: " + calculateSum());
    System.out.println("Среднее арифметическое: " + calculateAverage());
    System.out.println("Медиана: " + calculateMedian());
    System.out.println("Мода: " + findMode());
    System.out.println("Стандартное отклонение: " + calculateStandardDeviation());
    System.out.println("================================");
  }

  public static void main(String[] args) {
    int[] testData = {5};
    ArrayStatistics stats = new ArrayStatistics(testData);

    System.out.println("Исходный массив: " + Arrays.toString(testData));
    stats.printStatisticsReport();
    System.out.println("Элементов больше 5: " + stats.countGreaterThan(5));
    System.out.println("Элементов меньше 5: " + stats.countLessThan(5));
    System.out.println("Массив содержит 7: " + stats.contains(7));
    System.out.println("Массив содержит 10: " + stats.contains(10));
  }
}