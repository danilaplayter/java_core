package ru.mentee.power.collections.set;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TextAnalyzer {

  public static Set<String> findUniqueWords(String text) {
    if (text == null) {
      throw new IllegalArgumentException("Text cannot be null");
    }

    if (text.isEmpty()) {
      return new HashSet<>();
    }

    String[] words = text.toLowerCase().split("[\\s\\p{Punct}]+");
    return new HashSet<>(Arrays.asList(words));
  }

  public static Set<String> findCommonWords(String text1, String text2) {
    if (text1 == null || text2 == null) {
      throw new IllegalArgumentException("Texts cannot be null");
    }

    Set<String> set1 = findUniqueWords(text1);
    Set<String> set2 = findUniqueWords(text2);

    set1.retainAll(set2);
    return set1;
  }

  public static Set<String> findUniqueWordsInFirstText(String text1, String text2) {
    if (text1 == null || text2 == null) {
      throw new IllegalArgumentException("Texts cannot be null");
    }

    Set<String> set1 = findUniqueWords(text1);
    Set<String> set2 = findUniqueWords(text2);

    set1.removeAll(set2);
    return set1;
  }

  public static Set<String> findTopNWords(String text, int n) {
    if (text == null || n <= 0) {
      throw new IllegalArgumentException("Text cannot be null and n must be positive");
    }

    String[] words = text.toLowerCase().split("[\\s\\p{Punct}]+");
    Map<String, Integer> frequencyMap = new HashMap<>();

    for (String word : words) {
      frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
    }

    return frequencyMap.entrySet().stream()
        .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).limit(n).map(Map.Entry::getKey)
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  public static Set<Set<String>> findAnagrams(List<String> words) {
    if (words == null) {
      throw new IllegalArgumentException("Words list cannot be null");
    }

    Map<String, Set<String>> anagramGroups = new HashMap<>();

    for (String word : words) {
      char[] chars = word.toLowerCase().toCharArray();
      Arrays.sort(chars);
      String sorted = new String(chars);

      anagramGroups.computeIfAbsent(sorted, k -> new TreeSet<>()).add(word.toLowerCase());
    }

    return new HashSet<>(anagramGroups.values());
  }

  public static <T> boolean isSubset(Set<T> set1, Set<T> set2) {
    if (set1 == null || set2 == null) {
      throw new IllegalArgumentException("Sets cannot be null");
    }

    return set2.containsAll(set1);
  }

  public static String removeStopWords(String text, Set<String> stopWords) {
    if (text == null || stopWords == null) {
      throw new IllegalArgumentException("Text and stopWords cannot be null");
    }

    return Arrays.stream(text.split("[\\s\\p{Punct}]+"))
        .filter(word -> !stopWords.contains(word.toLowerCase())).collect(Collectors.joining(" "));
  }

  public static Map<String, Long> compareSetPerformance(List<String> words) {
    if (words == null) {
      throw new IllegalArgumentException("Words list cannot be null");
    }

    Map<String, Long> results = new HashMap<>();

    Set<String> hashSet = new HashSet<>();
    long startTime = System.nanoTime();
    for (String word : words) {
      hashSet.add(word);
    }
    results.put("HashSet add", System.nanoTime() - startTime);

    startTime = System.nanoTime();
    for (String word : words) {
      hashSet.contains(word);
    }
    results.put("HashSet contains", System.nanoTime() - startTime);

    startTime = System.nanoTime();
    for (String word : words) {
      hashSet.remove(word);
    }
    results.put("HashSet remove", System.nanoTime() - startTime);

    // Test LinkedHashSet
    Set<String> linkedHashSet = new LinkedHashSet<>();
    startTime = System.nanoTime();
    for (String word : words) {
      linkedHashSet.add(word);
    }
    results.put("LinkedHashSet add", System.nanoTime() - startTime);

    startTime = System.nanoTime();
    for (String word : words) {
      linkedHashSet.contains(word);
    }
    results.put("LinkedHashSet contains", System.nanoTime() - startTime);

    startTime = System.nanoTime();
    for (String word : words) {
      linkedHashSet.remove(word);
    }
    results.put("LinkedHashSet remove", System.nanoTime() - startTime);

    // Test TreeSet
    Set<String> treeSet = new TreeSet<>();
    startTime = System.nanoTime();
    for (String word : words) {
      treeSet.add(word);
    }
    results.put("TreeSet add", System.nanoTime() - startTime);

    startTime = System.nanoTime();
    for (String word : words) {
      treeSet.contains(word);
    }
    results.put("TreeSet contains", System.nanoTime() - startTime);

    startTime = System.nanoTime();
    for (String word : words) {
      treeSet.remove(word);
    }
    results.put("TreeSet remove", System.nanoTime() - startTime);

    return results;
  }
}