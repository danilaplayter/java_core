package ru.mentee.power.collections.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class CollectionAnalyzerTest {

  @Test
  void shouldReturnStringsLongerThanMinLength() {
    // Arrange
    Collection<String> strings = Arrays.asList("a", "abc", "abcde", "xy");
    int minLength = 2;

    List<String> result = CollectionAnalyzer.findLongStrings(strings, minLength);

    assertThat(result).hasSize(2).containsExactly("abc", "abcde");
  }

  @Test
  void shouldReturnEmptyListWhenCollectionIsNull() {
    List<String> result = CollectionAnalyzer.findLongStrings(null, 3);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldReturnEmptyListWhenCollectionIsEmpty() {
    Collection<String> emptyCollection = Collections.emptyList();

    List<String> result = CollectionAnalyzer.findLongStrings(emptyCollection, 3);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldIgnoreNullAndEmptyStringsWhenFindingLongStrings() {
    Collection<String> strings = Arrays.asList(null, "", "a", "abcd", "  ");
    int minLength = 1;

    List<String> result = CollectionAnalyzer.findLongStrings(strings, minLength);

    assertThat(result).hasSize(1).containsExactly("abcd");
  }

  @Test
  void shouldCalculateCorrectDigitSumForPositiveNumber() {
    int result = CollectionAnalyzer.calculateDigitSum(123);

    assertThat(result).isEqualTo(6); // 1 + 2 + 3 = 6
  }

  @Test
  void shouldCalculateCorrectDigitSumForNegativeNumber() {
    int result = CollectionAnalyzer.calculateDigitSum(-123);

    assertThat(result).isEqualTo(6); // |-1| + 2 + 3 = 6
  }

  @Test
  void shouldReturnZeroAsDigitSumForZero() {
    int result = CollectionAnalyzer.calculateDigitSum(0);

    assertThat(result).isZero();
  }

  @Test
  void shouldCountNumbersWithDigitSumGreaterThanThreshold() {
    Collection<Integer> numbers = Arrays.asList(12, 34, 55, 100, 7);
    int threshold = 5;

    int result = CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(numbers, threshold);

    assertThat(result).isEqualTo(3); // 12(3), 34(7), 55(10)
  }

  @Test
  void shouldReturnZeroWhenCountingWithNullCollection() {
    int result = CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(null, 5);

    assertThat(result).isZero();
  }

  @Test
  void shouldReturnZeroWhenCountingWithEmptyCollection() {
    Collection<Integer> emptyCollection = Collections.emptyList();

    int result = CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(emptyCollection, 5);

    assertThat(result).isZero();
  }

  @Test
  void shouldHandleCustomScenarioForDigitSumCount() {
    Collection<Integer> numbers = Arrays.asList(123, -456, 0, 999, 1000, null);
    int threshold = 10;

    int result = CollectionAnalyzer.countNumbersWithDigitSumGreaterThan(numbers, threshold);

    assertThat(result).isEqualTo(2);
  }
}