package ru.mentee.power.collections.set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TextAnalyzerTest {

  @Test
  @DisplayName("Метод findUniqueWords должен находить все уникальные слова в тексте")
  void shouldFindUniqueWordsInText() {
    String text = "Привет, мир! Привет всем в этом мире!";
    Set<String> expected = new HashSet<>(
        Arrays.asList("привет", "мир", "всем", "в", "этом", "мире"));

    Set<String> result = TextAnalyzer.findUniqueWords(text);

    assertThat(result)
        .isNotNull()
        .hasSize(6)
        .containsExactlyInAnyOrderElementsOf(expected);
  }

  @Test
  @DisplayName("Метод findUniqueWords должен выбросить исключение при null аргументе")
  void shouldThrowExceptionForNullTextInFindUniqueWords() {
    assertThatThrownBy(() -> TextAnalyzer.findUniqueWords(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Text cannot be null");
  }

  @Test
  @DisplayName("Метод findUniqueWords должен вернуть пустое множество для пустого текста")
  void shouldReturnEmptySetForEmptyTextInFindUniqueWords() {
    Set<String> result = TextAnalyzer.findUniqueWords("");
    assertThat(result)
        .isNotNull()
        .isEmpty();
  }

  @Test
  @DisplayName("Метод findCommonWords должен находить общие слова в двух текстах (операция пересечения)")
  void shouldFindCommonWordsInTexts() {
    String text1 = "apple banana orange";
    String text2 = "banana cherry kiwi orange";
    Set<String> expected = new HashSet<>(Arrays.asList("banana", "orange"));

    Set<String> result = TextAnalyzer.findCommonWords(text1, text2);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("Метод findCommonWords должен выбросить исключение при null аргументах")
  void shouldThrowExceptionForNullArgumentsInFindCommonWords() {
    assertThatThrownBy(() -> TextAnalyzer.findCommonWords(null, "text"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Texts cannot be null");

    assertThatThrownBy(() -> TextAnalyzer.findCommonWords("text", null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Texts cannot be null");
  }

  @Test
  @DisplayName("Метод findUniqueWordsInFirstText должен находить слова, уникальные для первого текста (операция разности)")
  void shouldFindUniqueWordsInFirstText() {
    String text1 = "apple banana orange";
    String text2 = "banana cherry kiwi";
    Set<String> expected = new HashSet<>(Arrays.asList("apple", "orange"));

    Set<String> result = TextAnalyzer.findUniqueWordsInFirstText(text1, text2);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("Метод findUniqueWordsInFirstText должен выбросить исключение при null аргументах")
  void shouldThrowExceptionForNullArgumentsInFindUniqueWordsInFirstText() {
    assertThatThrownBy(() -> TextAnalyzer.findUniqueWordsInFirstText(null, "text"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Texts cannot be null");

    assertThatThrownBy(() -> TextAnalyzer.findUniqueWordsInFirstText("text", null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Texts cannot be null");
  }

  @Test
  @DisplayName("Метод findTopNWords должен находить наиболее часто встречающиеся слова")
  void shouldFindTopNWords() {
    String text = "apple banana apple cherry banana apple";
    Set<String> expected = new LinkedHashSet<>(Arrays.asList("apple", "banana"));

    Set<String> result = TextAnalyzer.findTopNWords(text, 2);

    assertEquals(expected, result);
    assertEquals(2, result.size());
    assertTrue(result.contains("apple"));
    assertTrue(result.contains("banana"));
  }

  @Test
  @DisplayName("Метод findTopNWords должен выбросить исключение при некорректных аргументах")
  void shouldThrowExceptionForInvalidArgumentsInFindTopNWords() {
    assertThatThrownBy(() -> TextAnalyzer.findTopNWords(null, 1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Text cannot be null and n must be positive");

    assertThatThrownBy(() -> TextAnalyzer.findTopNWords("text", 0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Text cannot be null and n must be positive");

    assertThatThrownBy(() -> TextAnalyzer.findTopNWords("text", -1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Text cannot be null and n must be positive");
  }

  @Test
  @DisplayName("Метод findAnagrams должен находить группы анаграмм")
  void shouldFindAnagrams() {
    List<String> words = Arrays.asList("listen", "silent", "enlist", "hello", "world");
    Set<Set<String>> expected = new HashSet<>();
    expected.add(new TreeSet<>(Arrays.asList("listen", "silent", "enlist")));
    expected.add(new TreeSet<>(Arrays.asList("hello")));
    expected.add(new TreeSet<>(Arrays.asList("world")));

    Set<Set<String>> result = TextAnalyzer.findAnagrams(words);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("Метод findAnagrams должен выбросить исключение при null аргументе")
  void shouldThrowExceptionForNullArgumentInFindAnagrams() {
    assertThatThrownBy(() -> TextAnalyzer.findAnagrams(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Words list cannot be null");
  }

  @Test
  @DisplayName("Метод isSubset должен определять, является ли одно множество подмножеством другого")
  void shouldCheckIfSetIsSubset() {
    Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
    Set<Integer> set2 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

    boolean result1 = TextAnalyzer.isSubset(set1, set2);
    boolean result2 = TextAnalyzer.isSubset(set2, set1);

    assertTrue(result1);
    assertFalse(result2);
  }

  @Test
  @DisplayName("Метод isSubset должен выбросить исключение при null аргументах")
  void shouldThrowExceptionForNullArgumentsInIsSubset() {
    Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));

    assertThatThrownBy(() -> TextAnalyzer.isSubset(null, set))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Sets cannot be null");

    assertThatThrownBy(() -> TextAnalyzer.isSubset(set, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Sets cannot be null");
  }

  @Test
  @DisplayName("Метод removeStopWords должен удалять стоп-слова из текста")
  void shouldRemoveStopWordsFromText() {
    String text = "this is a sample text with some stop words";
    Set<String> stopWords = new HashSet<>(Arrays.asList("this", "is", "a", "with", "some"));
    String expected = "sample text stop words";

    String result = TextAnalyzer.removeStopWords(text, stopWords);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("Метод removeStopWords должен выбросить исключение при null аргументах")
  void shouldThrowExceptionForNullArgumentsInRemoveStopWords() {
    Set<String> stopWords = new HashSet<>(Arrays.asList("word"));

    assertThatThrownBy(() -> TextAnalyzer.removeStopWords(null, stopWords))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Text and stopWords cannot be null");

    assertThatThrownBy(() -> TextAnalyzer.removeStopWords("text", null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Text and stopWords cannot be null");
  }

  @Test
  @DisplayName("Метод compareSetPerformance должен сравнивать производительность разных типов множеств")
  void shouldCompareSetPerformance() {
    List<String> words = new ArrayList<>();
    for (int i = 0; i < 10000; i++) {
      words.add("word" + i);
    }

    Map<String, Long> results = TextAnalyzer.compareSetPerformance(words);

    assertNotNull(results);
    assertFalse(results.isEmpty());
    assertTrue(results.containsKey("HashSet add"));
    assertTrue(results.containsKey("LinkedHashSet contains"));
    assertTrue(results.containsKey("TreeSet remove"));

    // Verify that all operations have positive time measurements
    results.values().forEach(time -> assertTrue(time > 0));
  }
}