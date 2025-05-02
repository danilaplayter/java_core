package ru.mentee.power.collections.list;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArrayListAnalyzerTest {

  @Test
  @DisplayName("Метод filterByPrefix должен корректно фильтровать строки по префиксу")
  void shouldFilterByPrefixCorrectly() {
    List<String> input = new ArrayList<>(
        Arrays.asList("apple", "banana", "apricot", "orange", "app"));
    List<String> expected = Arrays.asList("apple", "apricot", "app");

    List<String> result = ArrayListAnalyzer.filterByPrefix(input, "ap");

    assertThat(result)
        .isNotNull()
        .hasSize(3)
        .containsExactlyElementsOf(expected);
  }

  @Test
  @DisplayName("Метод filterByPrefix должен выбросить исключение при null аргументах")
  void shouldThrowExceptionForNullArgumentsInFilterByPrefix() {
    assertThatThrownBy(() -> ArrayListAnalyzer.filterByPrefix(null, "test"))
        .isInstanceOf(IllegalArgumentException.class);

    assertThatThrownBy(() -> ArrayListAnalyzer.filterByPrefix(Arrays.asList("test"), null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Метод filterByPrefix должен вернуть пустой список, если нет совпадений")
  void shouldReturnEmptyListWhenNoMatchesInFilterByPrefix() {
    List<String> input = Arrays.asList("banana", "orange", "grape");
    List<String> result = ArrayListAnalyzer.filterByPrefix(input, "ap");

    assertThat(result)
        .isNotNull()
        .isEmpty();
  }

  @Test
  @DisplayName("Метод findMax должен корректно находить максимальный элемент")
  void shouldFindMaxCorrectly() {
    List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 8, 1, 9, 3));

    Integer result = ArrayListAnalyzer.findMax(numbers);

    assertEquals(9, result);
  }

  @Test
  @DisplayName("Метод findMax должен выбросить исключение для пустого списка или null")
  void shouldThrowExceptionForEmptyOrNullListInFindMax() {
    assertThatThrownBy(() -> ArrayListAnalyzer.findMax(null))
        .isInstanceOf(IllegalArgumentException.class);

    assertThatThrownBy(() -> ArrayListAnalyzer.findMax(Collections.emptyList()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Метод partition должен корректно разбивать список на части")
  void shouldPartitionListCorrectly() {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
    List<List<Integer>> result = ArrayListAnalyzer.partition(numbers, 3);

    assertThat(result)
        .isNotNull()
        .hasSize(3)
        .containsExactly(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5, 6),
            Collections.singletonList(7)
        );
  }

  @Test
  @DisplayName("Метод partition должен выбросить исключение при некорректных аргументах")
  void shouldThrowExceptionForInvalidArgumentsInPartition() {
    assertThatThrownBy(() -> ArrayListAnalyzer.partition(null, 2))
        .isInstanceOf(IllegalArgumentException.class);

    assertThatThrownBy(() -> ArrayListAnalyzer.partition(Arrays.asList(1, 2), 0))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Метод isPalindrome должен корректно определять палиндромы")
  void shouldIdentifyPalindromesCorrectly() {
    assertTrue(ArrayListAnalyzer.isPalindrome(Arrays.asList(1, 2, 3, 2, 1)));
    assertTrue(ArrayListAnalyzer.isPalindrome(Arrays.asList("a", "b", "a")));
    assertFalse(ArrayListAnalyzer.isPalindrome(Arrays.asList(1, 2, 3)));
    assertTrue(ArrayListAnalyzer.isPalindrome(Collections.singletonList("single")));
    assertTrue(ArrayListAnalyzer.isPalindrome(Collections.emptyList()));
  }

  @Test
  @DisplayName("Метод isPalindrome должен выбросить исключение при null аргументе")
  void shouldThrowExceptionForNullArgumentInIsPalindrome() {
    assertThatThrownBy(() -> ArrayListAnalyzer.isPalindrome(null))
        .isInstanceOf(IllegalArgumentException.class);
  }
}