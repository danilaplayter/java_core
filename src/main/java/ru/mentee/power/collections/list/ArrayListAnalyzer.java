package ru.mentee.power.collections.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArrayListAnalyzer {

  public static List<String> filterByPrefix(List<String> strings, String prefix) {
    List<String> listFilteredByPrefix = new ArrayList<>();
    if (strings == null || prefix == null) {
      throw new IllegalArgumentException("Либо список, либо префикс - null");
    }
    for (String str : strings) {
      if (str == null || str.isEmpty()) {
        continue;
      }
      if (str.startsWith(prefix)) {
        listFilteredByPrefix.add(str);
      }
    }
    return listFilteredByPrefix;
  }

  public static Integer findMax(List<Integer> numbers) {
    if (numbers == null || numbers.isEmpty()) {
      throw new IllegalArgumentException("Список null или пуст");
    }

    int maxNumber = numbers.getFirst();

    for (int number : numbers) {
      if (number > maxNumber) {
        maxNumber = number;
      }
    }
    return maxNumber;

  }

  public static <T> List<List<T>> partition(List<T> list, int partSize) {
    if (list == null || partSize <= 0) {
      throw new IllegalArgumentException("Список null или partSize <= 0");
    }

    List<List<T>> partitions = new ArrayList<>();
    int size = list.size();

    for (int i = 0; i < size; i += partSize) {
      int end = Math.min(i + partSize, size);
      partitions.add(new ArrayList<>(list.subList(i, end)));
    }

    return partitions;
  }

  public static <T> boolean isPalindrome(List<T> list) {
    if (list == null) {
      throw new IllegalArgumentException("Список null");
    }

    int size = list.size();
    for (int i = 0; i < size / 2; i++) {
      T left = list.get(i);
      T right = list.get(size - 1 - i);
      if (!Objects.equals(left, right)) {
        return false;
      }
    }
    return true;
  }

}